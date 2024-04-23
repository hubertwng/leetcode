package wang.hubert.leetcode.design.raft.core;

import java.util.List;

public class AppendEntriesParams {
    public  int term;
    public  int leaderId;
    public  List<LogEntry> entries;
    public  int leaderCommit;

    public static AppendEntriesParams heartbeatesParams(int leaderId, int term) {
        return new AppendEntriesParams(term, leaderId, null, 0);
    }

    public AppendEntriesParams(int term, int leaderId, List<LogEntry> entries, int leaderCommit) {
        this.term = term;
        this.leaderId = leaderId;
        this.entries = entries;
        this.leaderCommit = leaderCommit;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public List<LogEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LogEntry> entries) {
        this.entries = entries;
    }

    public int getLeaderCommit() {
        return leaderCommit;
    }

    public void setLeaderCommit(int leaderCommit) {
        this.leaderCommit = leaderCommit;
    }

    

}
