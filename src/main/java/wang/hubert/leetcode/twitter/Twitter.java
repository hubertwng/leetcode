package wang.hubert.leetcode.twitter;

import java.util.List;

public interface Twitter {
    /**
     * 发一篇推文
     * @param twitte
     */
    void postTweet(Twitte twitte);

    /**
     * 获取最新的多少条推文
     * @param userId 用户id
     * @param latestFeedsCount 最新的多少条推文
     * @return
     */
    List<Twitte> getNewsFeed(int userId, int latestFeedsCount);

    /**
     * 关注某人
     * @param followerId 发起关注者
     * @param followeeId 被关注者
     */
    void follow(int followerId, int followeeId);

    /**
     * 取关某人
     * @param followerId 发起关注者
     * @param followeeId 被关注者
     */
    void unfollow(int followerId, int followeeId);

}
