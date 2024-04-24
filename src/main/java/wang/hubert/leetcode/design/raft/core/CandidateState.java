package wang.hubert.leetcode.design.raft.core;
import java.util.concurrent.CompletableFuture;

public class CandidateState extends RaftState{

    private int votesReceived;

    private int totalVotes;

public CandidateState(RaftNode raftNode, RaftTransport transport) {
        super(raftNode, transport);
        this.votesReceived = 1; // 候选人首先给自己投票
        this.totalVotes = raftNode.getRaftConfiguration().getPeers().size() + 1; // 包括自身
    }

@Override
public void onEnterState() {
    raftNode.setCurrentTerm(raftNode.getCurrentTerm()+ 1);
    raftNode.setVotedFor(raftNode.getId());
    requestVotesFromAllPeers();
}

@Override
public void onExitState() {
    
}

 private void  requestVotesFromAllPeers() {
    RequestVoteParams requestVoteParams = new RequestVoteParams(
            raftNode.getCurrentTerm(),
            raftNode.getId(),
            raftNode.getRaftLog().getLastIndex(),
            raftNode.getRaftLog().getLastEntry().getTerm()
        );



    raftNode.getRaftConfiguration().getPeers().forEach(peer -> {
        CompletableFuture<VoteResponse> voteFuture = new CompletableFuture<>();
        voteFuture.thenAccept(voteResponse -> {
            handleVoteResponse(voteResponse);
        }).exceptionally(ex -> {
            System.out.println("Failed to get vote from " +peer.getId()  + " due to " + ex.getMessage());
            return null;
        });

        transport.sendRequestVote(peer, requestVoteParams, voteFuture);


    });
    
 }   
 private void handleVoteResponse(VoteResponse response) {
    if (response.getTerm() > raftNode.getCurrentTerm()) {
        raftNode.setCurrentTerm(response.getTerm());
        raftNode.becomeFollower();
    } else if (response.isVoteGranted()) {
        votesReceived++;
        if (votesReceived > totalVotes / 2) {
            raftNode.becomeLeader();
        }
    }
}

@Override
public VoteResponse handleRequestVote(RequestVoteParams params) {
    // 如果请求的任期小于当前任期，拒绝投票
    if (params.getTerm() < raftNode.getCurrentTerm()) {
        return new VoteResponse(false, raftNode.getCurrentTerm(), raftNode.getId());
    }

    // 请求的任期更高，变回Follower
    if (params.getTerm() > raftNode.getCurrentTerm()) {
        raftNode.setCurrentTerm(params.getTerm());
        raftNode.setVotedFor(0);
        raftNode.becomeFollower();
    }

    // 检查日志至少一样新
    boolean logIsAtLeastAsUpToDate = (params.getLastLogTerm() > raftNode.getRaftLog().getLastEntry().getTerm()) ||
    (params.getLastLogTerm() == raftNode.getRaftLog().getLastEntry().getTerm() && params.getLastLogIndex() >= raftNode.getRaftLog().getLastIndex());

    // 如果没有投票给其他候选人，并且日志足够新
    if (raftNode.getVotedFor() == 0 && logIsAtLeastAsUpToDate) {
        raftNode.setVotedFor(params.getCandidateId());
        return new VoteResponse(true, raftNode.getCurrentTerm(), raftNode.getId());
    }

    return new VoteResponse(true, raftNode.getCurrentTerm(), raftNode.getId());
}

@Override
public AppendEntriesResponse handleAppendEntries(AppendEntriesParams params) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handleAppendEntries'");
}
}
