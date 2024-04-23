package wang.hubert.leetcode.design.raft.core;

public abstract class RaftState implements IState{

    protected RaftNode raftNode;

    protected ImessageSender messageSender;


    public RaftState(RaftNode raftNode, ImessageSender mImessageSender) {
        this.raftNode = raftNode;
        this.messageSender = messageSender;
    }



}
