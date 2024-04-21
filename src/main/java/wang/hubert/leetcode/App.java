package wang.hubert.leetcode;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import wang.hubert.leetcode.twitter.Twitte;
import wang.hubert.leetcode.twitter.Twitter;
import wang.hubert.leetcode.twitter.memory.MemoryTwitter;
import wang.hubert.leetcode.twitter.topk.TopKTwitte;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Twitter twitter = new MemoryTwitter();

        for (int i = 0; i < 10; i++) {
            Integer userId = i + 1;
            System.out.println("******************************posts**************************" + userId);
            for (int j = 1; j <= 10; j++) {
                String content = userId + "->" + "conent" + ":" + j;
                System.out.println(content);
                twitter.postTweet(new Twitte(userId, content));
            }

        }

        twitter.follow(1, 2);
        twitter.follow(1, 3);
        twitter.follow(1, 4);


        List<Twitte> twittes = twitter.getNewsFeed(1, 10);
        System.out.println("******************************feeds**************************");
        for (Twitte twitte : twittes) {
            System.out.println(twitte.getContent());
        }
        
        twitter = new TopKTwitte(10);

        for (int i = 0; i < 10; i++) {
            Integer userId = i + 1;
            System.out.println("******************************posts**************************" + userId);
            for (int j = 1; j <= 10; j++) {
                String content = userId + "->" + "conent" + ":" + j;
                System.out.println(content);
                twitter.postTweet(new Twitte(userId, content));
            }

        }

        twitter.follow(1, 2);
        twitter.follow(1, 3);
        twitter.follow(1, 4);


        twittes = twitter.getNewsFeed(1, 10);
        System.out.println("******************************feeds**************************");
        for (Twitte twitte : twittes) {
            System.out.println(twitte.getContent());
        }
    }
}
