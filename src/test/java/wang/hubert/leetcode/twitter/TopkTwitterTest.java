package wang.hubert.leetcode.twitter;

import java.util.List;

import org.junit.Test;

import wang.hubert.leetcode.twitter.generator.AtomicTweetIdGenerator;
import wang.hubert.leetcode.twitter.generator.AtomicTweetTimestampGenerator;
import wang.hubert.leetcode.twitter.topk.TopKTwitte;

public class TopkTwitterTest {


    @Test
    public void testGetNewsFeed() {
        Twitter twitter = new TopKTwitte(10);
        TweetIdGenerator tweetIdGenerator = new AtomicTweetIdGenerator();
        TweetTimestampGenerator timestampGenerator = new AtomicTweetTimestampGenerator();
        for (int i = 0; i < 10; i++) {
            Integer userId = i + 1;
            System.out.println("******************************posts**************************" + userId);
            for (int j = 1; j <= 10; j++) {
                String content = userId + "->" + "conent" + ":" + j;
                System.out.println(content);
                twitter.postTweet(new Tweet(userId, content, tweetIdGenerator, timestampGenerator));
            }

        }

        twitter.follow(1, 2);
        twitter.follow(1, 3);
        twitter.follow(1, 4);


        List<Tweet> tweets = twitter.getNewsFeed(1, 10);
        System.out.println("******************************feeds**************************");
        for (Tweet tweet : tweets) {
            System.out.println(tweet.getContent());
        }
    }
}
