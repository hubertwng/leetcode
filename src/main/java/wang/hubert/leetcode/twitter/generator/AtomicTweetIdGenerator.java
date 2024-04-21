package wang.hubert.leetcode.twitter.generator;

import java.util.concurrent.atomic.AtomicInteger;

import wang.hubert.leetcode.twitter.TweetIdGenerator;

public class AtomicTweetIdGenerator implements TweetIdGenerator{
    private static final AtomicInteger generator = new AtomicInteger();
    @Override
    public int nextId() {
        return generator.incrementAndGet();
    }

}
