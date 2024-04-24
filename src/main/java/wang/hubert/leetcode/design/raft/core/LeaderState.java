package wang.hubert.leetcode.design.raft.core;

import java.util.Timer;
import java.util.TimerTask;

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
        heartbeatTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // todo 需要定义CompletableFuture
                raftNode.getRaftConfiguration().getPeers().
                forEach(peer -> transport.sendAppendEntries(peer, AppendEntriesParams.heartbeatesParams(raftNode.getId(), raftNode.getCurrentTerm()), null));
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAppendEntries'");
    }
}
