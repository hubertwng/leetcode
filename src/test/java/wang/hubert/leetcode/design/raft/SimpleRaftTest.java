package wang.hubert.leetcode.design.raft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import wang.hubert.leetcode.design.raft.core.Peer;
import wang.hubert.leetcode.design.raft.core.RaftConfiguration;
import wang.hubert.leetcode.design.raft.core.RaftNode;
import wang.hubert.leetcode.design.raft.core.RaftServerProtocol;
import wang.hubert.leetcode.design.raft.simple.SimpleRaftLog;
import wang.hubert.leetcode.design.raft.simple.SimpleRaftServer;
import wang.hubert.leetcode.design.raft.simple.SimpleRaftTransport;

public class SimpleRaftTest {


    @Test
    public void testRaft() {
        SimpleRaftTransport raftTransport = new SimpleRaftTransport();
        
        RaftConfiguration raftConfiguration1 = new RaftConfiguration();
        raftConfiguration1.setElectionTimeout(3000);
        raftConfiguration1.setHeartBeatsTime(500);
        raftConfiguration1.setNodeId(1);
        List<Peer> peers1 = new ArrayList<>();
        peers1.add(new Peer(2, "2"));
        peers1.add(new Peer(3, "3"));
        raftConfiguration1.setPeers(peers1);



        RaftNode node1 = new RaftNode(new SimpleRaftLog(), raftTransport, raftConfiguration1);
        RaftServerProtocol server1 = new SimpleRaftServer(node1);
        raftTransport.addRaftServer(node1.getId(), server1);

        // node 2
        RaftConfiguration raftConfiguration2 = new RaftConfiguration();
        raftConfiguration2.setElectionTimeout(3000);
        raftConfiguration2.setHeartBeatsTime(500);
        raftConfiguration2.setNodeId(1);
        List<Peer> peers2 = new ArrayList<>();
        peers2.add(new Peer(1, "1"));
        peers2.add(new Peer(3, "3"));
        raftConfiguration1.setPeers(peers2);

        RaftNode node2 = new RaftNode(new SimpleRaftLog(), raftTransport, raftConfiguration2);
        RaftServerProtocol server2 = new SimpleRaftServer(node2);


        raftTransport.addRaftServer(node2.getId(), server2);
        
        // node3
        RaftConfiguration raftConfiguration3 = new RaftConfiguration();
        raftConfiguration3.setElectionTimeout(3000);
        raftConfiguration3.setHeartBeatsTime(500);
        raftConfiguration2.setNodeId(1);
        List<Peer> peers3 = new ArrayList<>();
        peers2.add(new Peer(2, "2"));
        peers2.add(new Peer(1, "1"));
        raftConfiguration1.setPeers(peers3);

        RaftNode node3 = new RaftNode(new SimpleRaftLog(), raftTransport, raftConfiguration3);
        RaftServerProtocol server3 = new SimpleRaftServer(node3);


        raftTransport.addRaftServer(node3.getId(), server3);

        try {
            Thread.sleep(10000000000l);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
