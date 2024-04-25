package wang.hubert.leetcode.design.raft;

import java.util.ArrayList;
import java.util.List;

public class AppendEntriesParams {
    private int term;
    private int leaderId;
    private List<LogEntry> entries;
    private int leaderCommit;
    private int lastLogIndex;
    private int lastLogTerm;

    public static AppendEntriesParams heartbeatesParams(int term, int leaderId, int lastLogIndex, int lastLogTerm) {
        return new AppendEntriesParams(term, leaderId, new ArrayList<>(), lastLogIndex, lastLogIndex, lastLogTerm);
    }

   
    public AppendEntriesParams(int term, int leaderId, List<LogEntry> entries, int leaderCommit, int lastLogIndex,
            int lastLogTerm) {
        this.term = term;
        this.leaderId = leaderId;
        this.entries = entries;
        this.leaderCommit = leaderCommit;
        this.lastLogIndex = lastLogIndex;
        this.lastLogTerm = lastLogTerm;
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

    public int getLastLogIndex() {
        return lastLogIndex;
    }

    public int getLastLogTerm() {
        return lastLogTerm;
    }

    public void setLastLogIndex(int lastLogIndex) {
        this.lastLogIndex = lastLogIndex;
    }

    public void setLastLogTerm(int lastLogTerm) {
        this.lastLogTerm = lastLogTerm;
    }

    

}
