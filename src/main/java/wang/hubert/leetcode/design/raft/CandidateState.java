package wang.hubert.leetcode.design.raft;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CandidateState extends RaftState{

    private int votesReceived;

    private int totalVotes;

    private Timer electionTimer;

    private AtomicBoolean isLeader;

    private ScheduledExecutorService electionTimeoutScheduledExecutorService;

    private ScheduledFuture<?>  electionFuture;


public CandidateState(RaftNode raftNode, RaftTransport transport, ScheduledExecutorService electionTimeoutScheduledExecutorService) {
        super(raftNode, transport);
        this.electionTimeoutScheduledExecutorService = electionTimeoutScheduledExecutorService;
        this.votesReceived = 1; // 候选人首先给自己投票
        this.totalVotes = raftNode.getRaftConfiguration().getPeers().size() + 1; // 包括自身
        this.isLeader = new AtomicBoolean(false);
        this.raftNode.setCurrentTerm(raftNode.getCurrentTerm() + 1);
       
    }

@Override
public void onEnterState() {
    raftNode.setVotedFor(raftNode.getId());
    String format = "RaftNode[%d] became candidate at currentTerm:%d";
    System.out.println(String.format(format, raftNode.getId(), raftNode.getCurrentTerm()));
    requestVotesFromAllPeers();
}

@Override
public void onExitState() {
    if (electionFuture != null && !electionFuture.isDone()) {
        electionFuture.cancel(false);
    }
    String format = "RaftNode[%d] exit candidate at currentTerm:%d";
    System.out.println(String.format(format, raftNode.getId(), raftNode.getCurrentTerm()));
}


public void resetElectionTimer() {
    if (electionFuture != null && !electionFuture.isDone()) {
        electionFuture.cancel(false);
    }
    electionFuture = electionTimeoutScheduledExecutorService.schedule(() -> {
        raftNode.setCurrentTerm(raftNode.getCurrentTerm() + 1);
        requestVotesFromAllPeers();
    }, raftNode.randomElectionTimeout(), TimeUnit.MILLISECONDS);
}

 private void  requestVotesFromAllPeers() {
    RequestVoteParams requestVoteParams = new RequestVoteParams(
            raftNode.getCurrentTerm(),
            raftNode.getId(),
            raftNode.getRaftLog().getLastIndex(),
            raftNode.getRaftLog().getLastEntry().getTerm()
        );

    List<Peer> peers = raftNode.getRaftConfiguration().getPeers();
    
    for (Peer peer : peers) {
        CompletableFuture<VoteResponse> voteFuture = new CompletableFuture<>();
       
        voteFuture.thenAccept(voteResponse -> {
            handleVoteResponse(voteResponse);
        }).exceptionally(ex -> {
            System.out.println("Failed to get vote from " + peer.getId()  + " due to " + ex.getMessage());
            return null;
        });

        transport.sendRequestVote(peer, requestVoteParams, voteFuture);
    }
    
 }
 
 private void handleVoteResult() {
    if (votesReceived > totalVotes / 2) {
        String format = "RaftNode[%d]received the votes[%d] > totals[%d]/2, becomeing leader";
        System.out.println(String.format(format, raftNode.getId(), votesReceived, totalVotes));
        if (isLeader.compareAndSet(false, true)) {
            raftNode.becomeLeader();
        }    
    } else {
        // resetElectionTimer();
    }
 }
 private void handleVoteResponse(VoteResponse response) {
    if (response.getTerm() > raftNode.getCurrentTerm()) {
        raftNode.setCurrentTerm(response.getTerm());
        raftNode.becomeFollower();
    } else if (response.isVoteGranted()) {
        votesReceived++;
        handleVoteResult();
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
    return new AppendEntriesResponse(false, raftNode.getCurrentTerm());
}
}
