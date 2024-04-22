package wang.hubert.leetcode.design.breaker.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleEventCounter implements EventCounter{
    private AtomicInteger counter;

    

    public SimpleEventCounter() {
        this.counter = new AtomicInteger(0);
    }

    @Override
    public void record() {
        counter.incrementAndGet();
    }

    @Override
    public int getCount() {
        return counter.get();
    }

    @Override
    public void reset() {
        counter.set(0);
    }

}
