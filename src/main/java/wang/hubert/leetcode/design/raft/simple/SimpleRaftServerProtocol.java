package wang.hubert.leetcode.design.raft.simple;

import wang.hubert.leetcode.design.raft.core.AppendEntriesParams;
import wang.hubert.leetcode.design.raft.core.AppendEntriesResponse;
import wang.hubert.leetcode.design.raft.core.Peer;
import wang.hubert.leetcode.design.raft.core.RaftNode;
import wang.hubert.leetcode.design.raft.core.RaftServerProtocol;
import wang.hubert.leetcode.design.raft.core.RequestVoteParams;
import wang.hubert.leetcode.design.raft.core.VoteResponse;

public class SimpleRaftServerProtocol implements RaftServerProtocol{

    private RaftNode raftNode;


    @Override
    public VoteResponse sendRequestVote(Peer peer, RequestVoteParams params) {
        return raftNode.getCurrentState().handleRequestVote(params);
    }

    @Override
    public AppendEntriesResponse sendAppendEntries(Peer peer, AppendEntriesParams appendEntriesParams) {
        return raftNode.getCurrentState().handleAppendEntries(appendEntriesParams);
    }

}
