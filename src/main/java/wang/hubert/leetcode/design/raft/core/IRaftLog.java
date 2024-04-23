package wang.hubert.leetcode.design.raft.core;

import java.util.List;

public interface IRaftLog {
    
    void appendEntry(LogEntry entry);

    int getLastIndex();

    int getLastTerm();

    List<LogEntry> getEntriesFrom(int index);

}
