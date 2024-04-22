package wang.hubert.leetcode.design.breaker.counter;

public interface EventCounter {
    
    void record();

    int getCount();

    void reset();
}
