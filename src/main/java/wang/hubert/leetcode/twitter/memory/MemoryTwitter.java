package wang.hubert.leetcode.twitter.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import wang.hubert.leetcode.twitter.Tweet;
import wang.hubert.leetcode.twitter.Twitter;

public class MemoryTwitter implements Twitter{
    
    /**
     * 推文列表，每个用户发布的推文
     */
    private Map<Integer, LinkedTweet> tweets;

    /**
     * 关注列表
     */
    private Map<Integer, Set<Integer>> followings;

    public MemoryTwitter() {
        this.followings = new HashMap<>();
        this.tweets = new HashMap<>();
    }


    @Override
    public void postTweet(Tweet twitte) {
        LinkedTweet linkedTwitte = tweets.get(twitte.getUserId());
        if (linkedTwitte == null) {
            tweets.put(twitte.getUserId(), new LinkedTweet(twitte));
        } else {
            tweets.put(twitte.getUserId(), new LinkedTweet(twitte, linkedTwitte));
        }
        
    }

    @Override
    public List<Tweet> getNewsFeed(int userId, int latestFeedsCount) {
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>(latestFeedsCount, (o1, o2) -> o2.getTimestamp() - o1.getTimestamp());
        
        if (tweets.containsKey(userId)) {
            LinkedTweet linkedTwitte = tweets.get(userId);
            while (linkedTwitte != null) {
                maxHeap.offer(linkedTwitte.getTweet());
                linkedTwitte = linkedTwitte.getNext();
            }
        }
        Set<Integer> followees = followings.get(userId);
        if (followees != null && followees.size() > 0) {
            for (Integer followeeId : followees) {
                LinkedTweet linkedTwitte = tweets.get(followeeId);
                while (linkedTwitte != null) {
                    maxHeap.offer(linkedTwitte.getTweet());
                    linkedTwitte = linkedTwitte.getNext();
                }
            }
        }

        List<Tweet> result = new ArrayList<>(latestFeedsCount);

        for (int i = 0; i < latestFeedsCount; i++) {
            result.add(maxHeap.poll());
        }

        
        return result;
    }

    @Override
    public void follow(int followerId, int followeeId) {
        Set<Integer> follweeIds = followings.get(followeeId);
        if (follweeIds == null) {
            follweeIds = new HashSet<>();
            followings.put(followerId, follweeIds);
        }
        follweeIds.add(followeeId);
    }

    @Override
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> follweeIds = followings.get(followeeId);
        if (follweeIds == null) {
            return;
        }
        follweeIds.remove(followeeId);
    }

}
