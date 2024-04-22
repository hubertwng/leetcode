package wang.hubert.leetcode.design.breaker.counter;

import wang.hubert.leetcode.design.breaker.CircuitBreaker;
import wang.hubert.leetcode.design.breaker.CircuitBreakerConfig;
import wang.hubert.leetcode.design.breaker.CircuitBreakerState;
import wang.hubert.leetcode.design.breaker.ClosedCircuitBreakderState;
import wang.hubert.leetcode.design.breaker.HalfOpendedCircuitBreakderState;
import wang.hubert.leetcode.design.breaker.OpenedCircuitBreakderState;

public class CounterCircuitBreaker implements CircuitBreaker{

    private CircuitBreakerState circuitBreakerState;

    private CircuitBreakerConfig circuitBreakerConfig;

    private EventCounter failureCounter;;

    private long lastFailureTime;

    public CounterCircuitBreaker(CircuitBreakerConfig config, EventCounter failureCounter) {
        this.failureCounter = failureCounter;
        this.lastFailureTime = 0;
        this.circuitBreakerState = new ClosedCircuitBreakderState(this);
        this.circuitBreakerConfig = config;
    }

    @Override
    public void call(Runnable runnable) throws Exception {
        circuitBreakerState.call(runnable);
    }

    @Override
    public boolean isClosed() {
        return circuitBreakerState instanceof ClosedCircuitBreakderState;
    }

    @Override
    public boolean isOpened() {
        return circuitBreakerState instanceof OpenedCircuitBreakderState;
    }

    @Override
    public boolean isHalfOpened() {
        return circuitBreakerState instanceof HalfOpendedCircuitBreakderState;
    }

    @Override
    public void reset() {
       failureCounter.reset();
       lastFailureTime = 0;
    }

    @Override
    public CircuitBreakerState state() {
        return this.circuitBreakerState;
    }

    @Override
    public CircuitBreakerConfig config() {
        return this.circuitBreakerConfig;
    }

    @Override
    public int failureCount() {
        return this.failureCounter.getCount();
    }

    @Override
    public long lastFailureTime() {
        return this.lastFailureTime;
    }

    @Override
    public void incrementFailureCount() {
        this.failureCounter.record();
        this.lastFailureTime = System.currentTimeMillis();
    }

    @Override
    public boolean shouldTryReset() {
        return System.currentTimeMillis() - lastFailureTime > this.config().getTimeout();
    }

    @Override
    public void transitionTo(CircuitBreakerState state) {
        this.circuitBreakerState = state;
    }

    
}
