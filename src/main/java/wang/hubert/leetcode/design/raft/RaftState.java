package wang.hubert.leetcode.design.raft;

public abstract class RaftState implements IState{

    protected RaftNode raftNode;

    protected RaftTransport transport;


    public RaftState(RaftNode raftNode, RaftTransport transport) {
        this.raftNode = raftNode;
        this.transport = transport;
    }

    
    public abstract VoteResponse handleRequestVote(RequestVoteParams params);
    
    public abstract AppendEntriesResponse handleAppendEntries(AppendEntriesParams params);

}
