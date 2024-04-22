package wang.hubert.leetcode.design.twitter.memory;

import wang.hubert.leetcode.design.twitter.Tweet;

public class LinkedTweet {
    /**
     * 当前推文
     */
    private Tweet tweet;


    /**
     * 下一个推文
     */
    private LinkedTweet next;

    public LinkedTweet getNext() {
        return next;
    }

    public void setNext(LinkedTweet next) {
        this.next = next;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public LinkedTweet(Tweet tweet, LinkedTweet next) {
        this.tweet = tweet;
        this.next = next;
    }

    public LinkedTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    

    
}
