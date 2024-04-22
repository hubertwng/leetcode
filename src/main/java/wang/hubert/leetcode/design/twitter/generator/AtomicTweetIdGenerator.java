package wang.hubert.leetcode.design.twitter.generator;

import java.util.concurrent.atomic.AtomicInteger;

import wang.hubert.leetcode.design.twitter.TweetIdGenerator;

public class AtomicTweetIdGenerator implements TweetIdGenerator{
    private static final AtomicInteger generator = new AtomicInteger();
    @Override
    public int nextId() {
        return generator.incrementAndGet();
    }

}
