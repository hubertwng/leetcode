package wang.hubert.leetcode.design.raft.core;

import java.util.Timer;
import java.util.TimerTask;

public class FollowerState extends RaftState{

    private Timer heartbeatTimer;

    public FollowerState(RaftNode raftNode, RaftTransport transport) {
        super(raftNode, transport);
    }




    @Override
    public void onEnterState() {
        resetHeartbeatTimer();
    }

    @Override
    public void onExitState() {
        resetHeartbeatTimer();
    }


    private void resetHeartbeatTimer() {
        if (heartbeatTimer != null) {
            heartbeatTimer.cancel();
        }
        heartbeatTimer.schedule( new TimerTask() {

            @Override
            public void run() {
                System.out.println("Heartbeat missed in follower state, becoming candidate.");
                raftNode.becomeCandidateState();
            }
            
        }, raftNode.getRaftConfiguration().getElectionTimeout());

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
        
        if (params.getLeaderId() != raftNode.getVotedFor()) {
            return new AppendEntriesResponse(false, raftNode.getCurrentTerm());
        }
        resetHeartbeatTimer();
        params.getEntries().forEach(raftNode.getRaftLog()::appendEntry);
        return new AppendEntriesResponse(true, raftNode.getCurrentTerm());
    }


}
