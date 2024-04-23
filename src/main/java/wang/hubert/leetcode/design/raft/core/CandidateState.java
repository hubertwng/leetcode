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
            raftNode.getRaftLog().getLastTerm()
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
}
