package wang.hubert.leetcode.design.raft.core;

public abstract class RaftState implements IState{

    protected RaftNode raftNode;

    protected RaftTransport transport;


    public RaftState(RaftNode raftNode, RaftTransport transport) {
        this.raftNode = raftNode;
        this.transport = transport;
    }

}
