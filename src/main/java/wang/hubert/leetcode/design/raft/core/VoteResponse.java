package wang.hubert.leetcode.design.raft.core;

public class VoteResponse {
    private boolean voteGranted;
    private int term;
    private int peerId;

    public VoteResponse(boolean voteGranted, int term, int peerId) {
        this.voteGranted = voteGranted;
        this.term = term;
        this.peerId = peerId;
    }

    public boolean isVoteGranted() {
        return voteGranted;
    }

    public int getTerm() {
        return term;
    }

    public void setVoteGranted(boolean voteGranted) {
        this.voteGranted = voteGranted;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    
}
