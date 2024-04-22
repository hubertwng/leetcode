package wang.hubert.leetcode.design.breaker;

public class CircuitBreakerConfig {
    
    /**
     * 开启状态后尝试转换到半开状态的超时时间
     */
    private  long timeout;

    /**
     *  触发短路器开启状态的失败次数阈值
     */
    private  int failureThreshold;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int getFailureThreshold() {
        return failureThreshold;
    }

    public void setFailureThreshold(int failureThreshold) {
        this.failureThreshold = failureThreshold;
    }

    

}
