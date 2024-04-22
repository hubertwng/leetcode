package wang.hubert.leetcode.design.breaker.counter;

import java.util.LinkedList;
import java.util.Queue;

public class TimeWindowCounter implements EventCounter{

    private final long windowSizeInMillis;

    private final Queue<Long> eventTimestamps;
    
    


    public TimeWindowCounter(long windowSizeInMillis) {
        this.windowSizeInMillis = windowSizeInMillis;
        this.eventTimestamps = new LinkedList<>();
    }

    @Override
    public void record() {
        long now = System.currentTimeMillis();
        eventTimestamps.add(now);
        evictOldEvents(now);
    }

    @Override
    public synchronized  int getCount() {
        long now = System.currentTimeMillis();
        evictOldEvents(now);
        return eventTimestamps.size();
    }

    @Override
    public void reset() {
       evictOldEvents(System.currentTimeMillis());
    }

    /**
     * 清理超出时间窗口的老旧事件
     */
    private void evictOldEvents(long now) {
        while (!eventTimestamps.isEmpty() && now - eventTimestamps.peek() > windowSizeInMillis) {
            eventTimestamps.poll();
        }
    }

}
