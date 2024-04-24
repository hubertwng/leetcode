package wang.hubert.leetcode.design.raft.simple;

import java.util.ArrayList;
import java.util.List;

import wang.hubert.leetcode.design.raft.core.IRaftLog;
import wang.hubert.leetcode.design.raft.core.LogEntry;

public class SimpleRaftLog implements IRaftLog{
    private List<LogEntry> logEntries = new ArrayList<>();
    @Override
    public void appendEntry(LogEntry entry) {
        logEntries.add(entry);
    }

    @Override
    public int getLastIndex() {
        return logEntries.size() - 1;
    }

    @Override
    public LogEntry getLastEntry() {
        if (logEntries.size() > 0) {
            return logEntries.get(logEntries.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    public List<LogEntry> getEntriesFrom(int index) {
        if (index >= logEntries.size()) {
            return new ArrayList<>();
        }
        return logEntries.subList(index, logEntries.size());
    }

}
