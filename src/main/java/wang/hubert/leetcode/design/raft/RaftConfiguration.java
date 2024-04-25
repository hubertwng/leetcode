package wang.hubert.leetcode.design.raft;

import java.util.List;

public class RaftConfiguration {

    /**
     * 选举超时时间
     */
    private int electionTimeout;

    /**
     * 节点id
     */
    private Integer nodeId;

    /**
     * peer 
     */
    private List<Peer> peers;


    /**
     * 心跳时间间隔
     */
    private int heartBeatsTime;

    public int getElectionTimeout() {
        return electionTimeout;
    }

    public void setElectionTimeout(int electionTimeout) {
        this.electionTimeout = electionTimeout;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public void setPeers(List<Peer> peers) {
        this.peers = peers;
    }

    public int getHeartBeatsTime() {
        return heartBeatsTime;
    }

    public void setHeartBeatsTime(int heartBeatsTime) {
        this.heartBeatsTime = heartBeatsTime;
    }
    
    
}
