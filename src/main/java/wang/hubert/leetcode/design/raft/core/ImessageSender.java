package wang.hubert.leetcode.design.raft.core;

public interface ImessageSender {
    
    /**
     * 发送心跳
     * @param term
     * @param candidateId
     */
    void sendHeartbeat(int term, int leaderId);

    /**
     * 发起选举投票
     * @param term
     * @param candidateId
     */
    void requestVotes(int term, int candidateId);
}
