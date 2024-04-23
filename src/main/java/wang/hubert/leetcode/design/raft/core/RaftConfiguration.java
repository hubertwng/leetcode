package wang.hubert.leetcode.design.raft.core;

import java.util.List;

public class RaftConfiguration {

    /**
     * 选举超时时间
     */
    private int electionTimeout;

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * peer 
     */
    private List<String> peerIds;

    public int getElectionTimeout() {
        return electionTimeout;
    }

    public void setElectionTimeout(int electionTimeout) {
        this.electionTimeout = electionTimeout;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<String> getPeerIds() {
        return peerIds;
    }

    public void setPeerIds(List<String> peerIds) {
        this.peerIds = peerIds;
    }

    

    
}
