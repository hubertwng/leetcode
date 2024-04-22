package wang.hubert.leetcode.design.breaker;

import org.junit.Test;

import wang.hubert.leetcode.design.breaker.counter.CounterCircuitBreaker;
import wang.hubert.leetcode.design.breaker.counter.SimpleEventCounter;

public class SimpleCounterCircuitBreakerTest {

    @Test
    public void testCall() {
        CircuitBreakerConfig config = new CircuitBreakerConfig();
        config.setFailureThreshold(10);
        config.setTimeout(1000);
        CircuitBreaker breaker = new CounterCircuitBreaker(config, new SimpleEventCounter());

        for (int i = 0; i < 200; i++) {
            try {
                Thread.sleep(500); // 延迟模拟
                breaker.call(() -> {
                    if (Math.random() > 0.5) {
                        throw new RuntimeException("Random Failure");
                    }
                    System.out.println("Operation Succeeded");
                });
            } catch (Exception e) {
                System.out.println("Exception handled: " + e.getMessage());
            }
        }

        breaker.reset();
    }
    

}
