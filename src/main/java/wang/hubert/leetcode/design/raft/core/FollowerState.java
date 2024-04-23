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


}
