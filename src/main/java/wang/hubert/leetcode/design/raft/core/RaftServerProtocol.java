package wang.hubert.leetcode.design.raft.core;

public interface RaftServerProtocol {
    VoteResponse sendRequestVote(Peer peer, RequestVoteParams params);

    AppendEntriesResponse sendAppendEntries(Peer peer, AppendEntriesParams appendEntriesParams);
}
