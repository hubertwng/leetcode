package wang.hubert.leetcode.design.raft;

public interface RaftServerProtocol {
    VoteResponse handleVote(RequestVoteParams params);

    AppendEntriesResponse handleAppendEntries(AppendEntriesParams appendEntriesParams);
}
