package wang.hubert.leetcode.design.raft.core;

public class FollowerState extends RaftState{



    public FollowerState(RaftNode raftNode, ImessageSender mImessageSender) {
        super(raftNode, mImessageSender);
    }




    @Override
    public void onEnterState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnterState'");
    }

    @Override
    public void onExitState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onExitState'");
    }


    private void resetHeartbeatTimer() {
        

    }

}
