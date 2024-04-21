package wang.hubert.leetcode.twitter.memory;

import wang.hubert.leetcode.twitter.Twitte;

public class LinkedTwitte {
    /**
     * 当前推文
     */
    private Twitte twitte;

    public Twitte getTwitte() {
        return twitte;
    }

    public void setTwitte(Twitte twitte) {
        this.twitte = twitte;
    }

    /**
     * 下一个推文
     */
    private LinkedTwitte next;

    public LinkedTwitte getNext() {
        return next;
    }

    public void setNext(LinkedTwitte next) {
        this.next = next;
    }

    public LinkedTwitte(Twitte twitte) {
        this.twitte = twitte;
    }

    public LinkedTwitte(Twitte twitte, LinkedTwitte next) {
        this.twitte = twitte;
        this.next = next;
    }

}
