package wang.hubert.leetcode.design.breaker;

public class ClosedCircuitBreakderState extends CircuitBreakerState{

    public ClosedCircuitBreakderState(CircuitBreaker breaker) {
        super(breaker);
    }

    @Override
    public void call(Runnable runnable) throws Exception {
        try {
            runnable.run();
        } catch(Exception e) {
            breaker.incrementFailureCount();
            if (breaker.failureCount() > breaker.config().getFailureThreshold()) {
                breaker.transitionTo(new OpenedCircuitBreakderState(super.breaker));
                throw new RuntimeException("[CircuitBreakder]breaker is open");
            }
        }
    }

   
}
