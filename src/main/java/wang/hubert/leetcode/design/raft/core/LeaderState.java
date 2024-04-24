package wang.hubert.leetcode.design.raft.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class LeaderState extends RaftState{
    
    private Timer heartbeatTimer;

    public LeaderState(RaftNode raftNode, RaftTransport transport) {
        super(raftNode,  transport);
    }

    @Override
    public void onEnterState() {
        sendHeartbeates();
    }

    @Override
    public void onExitState() {
        heartbeatTimer.cancel();
    }

    private void sendHeartbeates() {
        heartbeatTimer = new Timer();
        CompletableFuture<AppendEntriesResponse> future = new CompletableFuture<>();
        future.thenAccept(appendEntriesResponse -> {
            if (!appendEntriesResponse.isSuccess() && raftNode.getCurrentTerm() < appendEntriesResponse.getTerm()) {
                raftNode.setCurrentTerm(appendEntriesResponse.getTerm());
                raftNode.setVotedFor(0);
                raftNode.becomeFollower();
            }
        });
        LogEntry lasEntry = raftNode.getRaftLog().getLastEntry();
        AppendEntriesParams params = AppendEntriesParams.heartbeatesParams(raftNode.getId(), raftNode.getCurrentTerm(), raftNode.getRaftLog().getLastIndex(), lasEntry.getTerm());
        heartbeatTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // todo 需要定义CompletableFuture
                raftNode.getRaftConfiguration().getPeers().
                forEach(peer -> transport.sendAppendEntries(peer,params, future));
            }
            
        }, 0, raftNode.getRaftConfiguration().getHeartBeatsTime());
    }

    @Override
    public VoteResponse handleRequestVote(RequestVoteParams params) {
        // 如果请求的任期小于等于当前任期，作为leader，拒绝投票请求
        if (params.getTerm() <= raftNode.getCurrentTerm()) {
            return new VoteResponse(false, raftNode.getCurrentTerm(), raftNode.getId());
        }

        // 更新当前任期，并且重置投票人
        raftNode.setCurrentTerm(params.getTerm());
        raftNode.setVotedFor(0);
        raftNode.becomeFollower();

        // 检查日志至少一样新
        boolean logIsAtLeastAsUpToDate = (params.getLastLogTerm() > raftNode.getRaftLog().getLastEntry().getTerm()) ||
                                        (params.getLastLogTerm() == raftNode.getRaftLog().getLastEntry().getTerm() && params.getLastLogIndex() >= raftNode.getRaftLog().getLastIndex());


        if (logIsAtLeastAsUpToDate) {
            // 日志一样新同意投票
            raftNode.setVotedFor(params.getCandidateId());
            return new VoteResponse(true, raftNode.getCurrentTerm(), raftNode.getId());
        } else {
            // 日志不一样新不同意投票
            return new VoteResponse(false, raftNode.getCurrentTerm(), raftNode.getId());
        }
        
    }

    @Override
    public AppendEntriesResponse handleAppendEntries(AppendEntriesParams params) {
        if (params.getTerm() < raftNode.getCurrentTerm() ) {
            // 对方任期小于当前任期，拒绝请求
            return new AppendEntriesResponse(false, raftNode.getCurrentTerm());
        } else if(params.getTerm() == raftNode.getCurrentTerm()) {
                // 出现意外情况，两个 Leader 拥有相同的任期
                System.err.println("Conflict: two leaders with the same term");
                return new AppendEntriesResponse(false, raftNode.getCurrentTerm());
        } else {
             // 对方任期大于当前任期，退回到 Follower 状态
             raftNode.setCurrentTerm(params.getTerm());
             raftNode.becomeFollower();
             raftNode.setVotedFor(0);
             return new AppendEntriesResponse(true, raftNode.getCurrentTerm());
        }
    }
}
