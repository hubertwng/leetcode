package wang.hubert.leetcode.design.raft;

import java.util.concurrent.CompletableFuture;

public interface RaftTransport {
    
    void sendRequestVote(Peer peer, RequestVoteParams params, CompletableFuture<VoteResponse> future);


    void sendAppendEntries(Peer peer, AppendEntriesParams appendEntriesParams, CompletableFuture<AppendEntriesResponse> future);
}
