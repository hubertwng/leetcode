package wang.hubert.leetcode.design.raft;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FollowerState extends RaftState{

    private ScheduledExecutorService electionTimeoutScheduledExecutorService;
    private ScheduledFuture<?> electionTimeoutFuture;

    public FollowerState(RaftNode raftNode, RaftTransport transport, ScheduledExecutorService electionTimeoutScheduledExecutorService) {
        super(raftNode, transport);
        this.electionTimeoutScheduledExecutorService = electionTimeoutScheduledExecutorService;
    }




    @Override
    public void onEnterState() {
        String format = "RaftNode[%d] became follower at currentTerm:%d";
        System.out.println(String.format(format, raftNode.getId(), raftNode.getCurrentTerm()));
        resetElectionTimer();
    }

    @Override
    public void onExitState() {
        if (electionTimeoutFuture != null && !electionTimeoutFuture.isDone()) {
            electionTimeoutFuture.cancel(false);
        }
    }


    private void resetElectionTimer() {
        if (electionTimeoutFuture != null && !electionTimeoutFuture.isDone()) {
            electionTimeoutFuture.cancel(false);
        }

        this.electionTimeoutFuture = electionTimeoutScheduledExecutorService.schedule(() -> {
            String format = "RaftNode[%d] Heartbeat missed in follower state, becoming candidate.";
            System.out.println(String.format(format, raftNode.getId()));
            raftNode.becomeCandidateState();
        }, raftNode.randomElectionTimeout(), TimeUnit.MILLISECONDS);
    }




    @Override
    public VoteResponse handleRequestVote(RequestVoteParams params) {
        // 请求的任期小于当前任期，拒绝投票
        if (params.getTerm() < raftNode.getCurrentTerm()) {
            return new VoteResponse(false, raftNode.getCurrentTerm(), raftNode.getId());
        }

         // 检查候选人的日志是否至少和本节点的日志一样新
        LogEntry lastEntry = raftNode.getRaftLog().getLastEntry();
        int lastLogIndex = raftNode.getRaftLog().getLastIndex();
        int lastLogTerm =lastEntry != null ? lastEntry.getTerm() : 0;
        boolean isLogUpToDate = (params.getLastLogTerm() > lastLogTerm) ||
        (params.getLastLogTerm() == lastLogTerm && params.getLastLogIndex() >= lastLogIndex);
         
        // 如果请求的任期大于当前任期，更新当前任期，重置投票
        if (params.getTerm() > raftNode.getCurrentTerm()) {
            raftNode.setCurrentTerm(params.getTerm());
            raftNode.setVotedFor(0);
        }
        
        // 如果没有投过票或者已经投票给这个候选人了，并且日志足够新，同意投票请求
        if ((raftNode.getVotedFor() == 0 || raftNode.getVotedFor() == params.getCandidateId()) && isLogUpToDate) {
            raftNode.setVotedFor(params.getCandidateId());
            return new VoteResponse(true, raftNode.getCurrentTerm(), raftNode.getId());
        }

        return new VoteResponse(false, raftNode.getCurrentTerm(), raftNode.getId());
    }




    @Override
    public AppendEntriesResponse handleAppendEntries(AppendEntriesParams params) {
        if (params.getTerm() < raftNode.getCurrentTerm()) {
            return new AppendEntriesResponse(false, raftNode.getCurrentTerm());
        }
        if (params.getTerm() == raftNode.getCurrentTerm() && params.getLeaderId() != raftNode.getVotedFor()) {
            return new AppendEntriesResponse(false, raftNode.getCurrentTerm());
        }
        resetElectionTimer();
        if (params.getLeaderId() == raftNode.getId()) {
            System.out.println();
        }
        String formant = "RaftNode[%d]Follower receive a entry and reset election time at term[%d] leader[%d]";
        System.out.println(String.format(formant, raftNode.getId(), raftNode.getCurrentTerm(), params.getLeaderId()));
        params.getEntries().forEach(raftNode.getRaftLog()::appendEntry);
        return new AppendEntriesResponse(true, raftNode.getCurrentTerm());
    }

}
