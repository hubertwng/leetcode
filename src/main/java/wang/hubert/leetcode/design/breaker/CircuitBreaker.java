package wang.hubert.leetcode.design.breaker;

public interface CircuitBreaker extends Invoker{
    /**
     * 检查短路器是否处于关闭状态
     * @return
     */
    boolean isClosed();

    /**
     * 检查短路器是否处于开启状态
     * @return
     */
    boolean isOpened();
    
    /**
     * 检查短路器是否处于半开启状态
     * @return
     */
    boolean isHalfOpened();
    
    /**
     * 重置短路器到关闭状态
     */
    void reset();

    /**
     * 短路器状态
     * @return
     */
    CircuitBreakerState state();

    /**
     * 短路器配置信息
     * @return
     */
    CircuitBreakerConfig config();

    /**
     * 失败次数
     * @return
     */
    int failureCount();

    /**
     * 最后一次失败时间
     * @return
     */
    long lastFailureTime();

    /**
     * 统计失败次数
     */
    void incrementFailureCount();

    /**
     * 是否能够重置半开启
     * @return
     */
    boolean shouldTryReset();

    /**
     * 变换状态
     * @param state
     */
    void transitionTo(CircuitBreakerState state);

}
