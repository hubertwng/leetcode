package wang.hubert.leetcode.design.breaker;

import javax.management.RuntimeErrorException;

public class HalfOpendedCircuitBreakderState extends CircuitBreakerState {

    public HalfOpendedCircuitBreakderState(CircuitBreaker breaker) {
        super(breaker);
    }

    @Override
    public void call(Runnable runnable) throws Exception {
       try {
            runnable.run();
            breaker.transitionTo(new ClosedCircuitBreakderState(breaker));
            breaker.reset();
       } catch(Exception e) {
            breaker.incrementFailureCount();
            breaker.transitionTo(new OpenedCircuitBreakderState(breaker));
            throw new RuntimeException("[CircuitBreakder]breaker is half-open");
       }
    }

}
