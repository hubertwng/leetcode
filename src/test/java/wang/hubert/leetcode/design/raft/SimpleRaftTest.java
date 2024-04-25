package wang.hubert.leetcode.design.raft;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class SimpleRaftTest {


    @Test
    public void testRaft() {


        IRaftLog raftLog = new IRaftLog() {
            List<LogEntry> logEntries = new ArrayList<>();
            @Override
            public void appendEntry(LogEntry entry) {
                logEntries.add(entry);
            }

            @Override
            public int getLastIndex() {
                if (logEntries.size() == 0) {
                    return 0;
                }
                return logEntries.size() - 1;
            }

            @Override
            public LogEntry getLastEntry() {
                if (logEntries.size() == 0) {
                    return new LogEntry(0, "O");
                }
                return logEntries.get(logEntries.size() - 1);
            }

            @Override
            public List<LogEntry> getEntriesFrom(int index) {
                return logEntries.subList(index, logEntries.size());
            }
        };

        Map<Integer, RaftNode> NODES = new HashMap<>();
        RaftTransport raftTransport = new RaftTransport() {
            @Override
            public void sendRequestVote(Peer peer, RequestVoteParams params, CompletableFuture<VoteResponse> future) {
                if (!NODES.containsKey(peer.getId())) {
                    future.complete(new VoteResponse(false, params.getTerm(), params.getCandidateId()));
                    return;
                }
                future.complete(NODES.get(peer.getId()).getCurrentState().handleRequestVote(params));
            }

            @Override
            public void sendAppendEntries(Peer peer, AppendEntriesParams params, CompletableFuture<AppendEntriesResponse> future) {
                if (!NODES.containsKey(peer.getId())) {
                    future.complete(new AppendEntriesResponse(false, params.getTerm()));
                    return;
                }
                try {
                    Thread.sleep(new Random().nextInt(50));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                future.complete(NODES.get(peer.getId()).getCurrentState().handleAppendEntries(params));
            }
        };

        new Thread( () -> {
            ScheduledExecutorService executorService  = Executors.newScheduledThreadPool(2);
            RaftConfiguration raftConfiguration = new RaftConfiguration();
            raftConfiguration.setElectionTimeout(150);
            raftConfiguration.setHeartBeatsTime(50);
            raftConfiguration.setNodeId(1);
            List<Peer> peers = new ArrayList<>();
            peers.add(new Peer(2, "2"));
            peers.add(new Peer(3, "3"));
            raftConfiguration.setPeers(peers);

            RaftNode raftNode1 = new RaftNode(raftLog, raftTransport, raftConfiguration, executorService, executorService);
            NODES.put(raftNode1.getId(), raftNode1);
        }).start();

        new Thread(() -> {
            ScheduledExecutorService executorService  = Executors.newScheduledThreadPool(2);
            RaftConfiguration raftConfiguration = new RaftConfiguration();
            raftConfiguration.setElectionTimeout(150);
            raftConfiguration.setHeartBeatsTime(50);
            raftConfiguration.setNodeId(2);
            List<Peer> peers = new ArrayList<>();
            peers.add(new Peer(1, "1"));
            peers.add(new Peer(3, "3"));
            raftConfiguration.setPeers(peers);

            RaftNode raftNode2 = new RaftNode(raftLog, raftTransport, raftConfiguration, executorService, executorService);
            NODES.put(raftNode2.getId(), raftNode2);

        }).start();


        new Thread(() -> {
            ScheduledExecutorService executorService  = Executors.newScheduledThreadPool(2);
            RaftConfiguration raftConfiguration = new RaftConfiguration();
            raftConfiguration.setElectionTimeout(150);
            raftConfiguration.setHeartBeatsTime(50);
            raftConfiguration.setNodeId(3);
            List<Peer> peers = new ArrayList<>();
            peers.add(new Peer(1, "1"));
            peers.add(new Peer(2, "2"));
            raftConfiguration.setPeers(peers);

            RaftNode raftNode3 = new RaftNode(raftLog, raftTransport, raftConfiguration, executorService, executorService);
            NODES.put(raftNode3.getId(), raftNode3);
        }).start();

        try {
            Thread.sleep(100000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
