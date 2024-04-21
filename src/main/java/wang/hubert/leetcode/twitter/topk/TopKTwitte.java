package wang.hubert.leetcode.twitter.topk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import wang.hubert.leetcode.twitter.Twitte;
import wang.hubert.leetcode.twitter.Twitter;

public class TopKTwitte implements Twitter{

    /**
     * 推文
     */
    private Map<Integer, LinkedList<Twitte>> twittes;

    /**
     * 关注
     */
    private Map<Integer, Set<Integer>> followings;


    private int topK;
    
    private static final AtomicInteger twitteOrder = new AtomicInteger();


    public TopKTwitte(int topK) {
        this.twittes = new HashMap<Integer, LinkedList<Twitte>>();
        this.followings = new HashMap<>();
        this.topK = topK;
    }



    @Override
    public void postTweet(Twitte twitte) {
        twitte.setTimestamp(twitteOrder.incrementAndGet());
        LinkedList<Twitte> linkedTwittes = twittes.get(twitte.getUserId());
        if (linkedTwittes == null) {
            linkedTwittes = new LinkedList<>();
            twittes.put(twitte.getUserId(), linkedTwittes);
        }
        if (linkedTwittes.size() > topK) {
            linkedTwittes.removeFirst();
        }
        linkedTwittes.addLast(twitte);
    }

    @Override
    public List<Twitte> getNewsFeed(int userId, int latestFeedsCount) {
        if (latestFeedsCount > topK) {
            throw new UnsupportedOperationException("latestFeedsCount overflow the topKSize");
        }
        PriorityQueue<Twitte> maxHeap = new PriorityQueue<>(topK, (o1, o2) -> o2.getTimestamp() - o1.getTimestamp());
        
        if (twittes.containsKey(userId)) {
            LinkedList<Twitte> linkedTwitte = twittes.get(userId);
            linkedTwitte.forEach(tw -> maxHeap.offer(tw));
        }

        Set<Integer> followeeIds = followings.get(userId);
        if (followeeIds != null && followeeIds.size() > 0) {
            followeeIds.forEach(followeeId -> {
                if (twittes.containsKey(followeeId)) {
                    twittes.get(followeeId).forEach(tw -> maxHeap.offer(tw));
                }
            });
        }
        
        List<Twitte> result = new ArrayList<>();
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