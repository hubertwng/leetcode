package wang.hubert.leetcode.design.breaker;

public interface Invoker {
    void call(Runnable runnable) throws Exception;
}
