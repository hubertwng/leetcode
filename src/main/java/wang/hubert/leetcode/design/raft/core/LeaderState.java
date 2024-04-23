package wang.hubert.leetcode.design.raft.core;

public class LeaderState extends RaftState{

    public LeaderState(RaftNode raftNode, ImessageSender mImessageSender) {
        super(raftNode, mImessageSender);
        //TODO Auto-generated constructor stub
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

}
