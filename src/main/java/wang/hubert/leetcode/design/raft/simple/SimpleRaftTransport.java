package wang.hubert.leetcode.design.raft.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import wang.hubert.leetcode.design.raft.core.AppendEntriesParams;
import wang.hubert.leetcode.design.raft.core.AppendEntriesResponse;
import wang.hubert.leetcode.design.raft.core.Peer;
import wang.hubert.leetcode.design.raft.core.RaftServerProtocol;
import wang.hubert.leetcode.design.raft.core.RaftTransport;
import wang.hubert.leetcode.design.raft.core.RequestVoteParams;
import wang.hubert.leetcode.design.raft.core.VoteResponse;

public class SimpleRaftTransport implements RaftTransport{

    private Map<Integer, RaftServerProtocol> nodeContext;

    public SimpleRaftTransport() {
        nodeContext = new HashMap<>();
    }
    public void addRaftServer(Integer nodeId, RaftServerProtocol raftServer) {
        nodeContext.put(nodeId, raftServer);
    }

    @Override
    public void sendRequestVote(Peer peer, RequestVoteParams params, CompletableFuture<VoteResponse> future) {
        VoteResponse voteResponse = nodeContext.get(peer.getId()).sendRequestVote(peer, params);
        future.complete(voteResponse);
    }

    @Override
    public void sendAppendEntries(Peer peer, AppendEntriesParams appendEntriesParams,
            CompletableFuture<AppendEntriesResponse> future) {
        AppendEntriesResponse appendEntriesResponse = nodeContext.get(peer.getId()).sendAppendEntries(peer, appendEntriesParams);
        future.complete(appendEntriesResponse);
    }

}
