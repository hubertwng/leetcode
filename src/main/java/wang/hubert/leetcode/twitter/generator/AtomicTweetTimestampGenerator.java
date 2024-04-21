package wang.hubert.leetcode.twitter.generator;

import java.util.concurrent.atomic.AtomicInteger;

import wang.hubert.leetcode.twitter.TweetTimestampGenerator;

public class AtomicTweetTimestampGenerator implements TweetTimestampGenerator{
    private static final AtomicInteger generator = new AtomicInteger();
    @Override
    public int timestamp() {
        return generator.incrementAndGet();
    }

}
