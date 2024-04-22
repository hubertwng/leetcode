package wang.hubert.leetcode.design.twitter.topk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import wang.hubert.leetcode.design.twitter.Tweet;
import wang.hubert.leetcode.design.twitter.Twitter;

public class TopKTwitte implements Twitter{

    /**
     * 推文
     */
    private Map<Integer, LinkedList<Tweet>> tweets;

    /**
     * 关注
     */
    private Map<Integer, Set<Integer>> followings;


    private int topK;


    public TopKTwitte(int topK) {
        this.tweets = new HashMap<Integer, LinkedList<Tweet>>();
        this.followings = new HashMap<>();
        this.topK = topK;
    }



    @Override
    public void postTweet(Tweet twitte) {
        LinkedList<Tweet> linkedtweets = tweets.get(twitte.getUserId());
        if (linkedtweets == null) {
            linkedtweets = new LinkedList<>();
            tweets.put(twitte.getUserId(), linkedtweets);
        }
        if (linkedtweets.size() > topK) {
            linkedtweets.removeFirst();
        }
        linkedtweets.addLast(twitte);
    }

    @Override
    public List<Tweet> getNewsFeed(int userId, int latestFeedsCount) {
        if (latestFeedsCount > topK) {
            throw new UnsupportedOperationException("latestFeedsCount overflow the topKSize");
        }
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>(topK, (o1, o2) -> o2.getTimestamp() - o1.getTimestamp());
        
        if (tweets.containsKey(userId)) {
            LinkedList<Tweet> linkedTwitte = tweets.get(userId);
            linkedTwitte.forEach(tw -> maxHeap.offer(tw));
        }

        Set<Integer> followeeIds = followings.get(userId);
        if (followeeIds != null && followeeIds.size() > 0) {
            followeeIds.forEach(followeeId -> {
                if (tweets.containsKey(followeeId)) {
                    tweets.get(followeeId).forEach(tw -> maxHeap.offer(tw));
                }
            });
        }
        
        List<Tweet> result = new ArrayList<>();
        for (int i = 0; i < latestFeedsCount; i++) {
            if (maxHeap.isEmpty()) {
                break;
            }
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
