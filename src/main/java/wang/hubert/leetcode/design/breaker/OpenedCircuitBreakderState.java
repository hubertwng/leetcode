package wang.hubert.leetcode.design.breaker;

public class OpenedCircuitBreakderState extends CircuitBreakerState{

    public OpenedCircuitBreakderState(CircuitBreaker breaker) {
        super(breaker);
    }

    @Override
    public void call(Runnable runnable) throws Exception {
        if (System.currentTimeMillis() - breaker.lastFailureTime() > breaker.config().getTimeout()) {
            breaker.transitionTo(new HalfOpendedCircuitBreakderState(breaker));
            breaker.call(runnable);
        } else {
            throw new RuntimeException("[CircuitBreakder]breaker is open");
        }
    }

}
