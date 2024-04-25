package wang.hubert.leetcode.design.raft;

public class AppendEntriesResponse {
    private boolean success;
    private int term;

    public AppendEntriesResponse(boolean success, int term) {
        this.success = success;
        this.term = term;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
    
}
