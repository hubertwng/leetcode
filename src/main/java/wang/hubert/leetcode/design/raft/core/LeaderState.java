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
}
