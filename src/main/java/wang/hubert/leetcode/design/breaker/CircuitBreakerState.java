package wang.hubert.leetcode.design.breaker;

public abstract class CircuitBreakerState implements Invoker{

    protected CircuitBreaker breaker;

    public CircuitBreakerState(CircuitBreaker breaker) {
        this.breaker = breaker;
    }

    public void reset() {
        breaker.reset();
    }
}
