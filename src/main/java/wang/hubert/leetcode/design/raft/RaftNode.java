package wang.hubert.leetcode.design.raft;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

public class RaftNode {

    private int id;

    /**
     * 当前任期
     */
    private int currentTerm;
    
    /**
     * 投给谁了
     */
    private int votedFor;

    /**
     * 当前状态
     */
    private RaftState currentState;

    /**
     * 日志记录器
     */
    private IRaftLog raftLog;

    /**
     * 消息发送器
     */
    private RaftTransport raftTransport;

    private RaftConfiguration raftConfiguration;

    private ScheduledExecutorService electionTimeoutScheduledExecutorService;

    private ScheduledExecutorService heartbeatstScheduledExecutorService;


    public RaftNode(IRaftLog raftLog, RaftTransport raftTransport, RaftConfiguration configuration, ScheduledExecutorService electionTimeoutScheduledExecutorService,  ScheduledExecutorService heartbeatstScheduledExecutorService) {
        this.raftConfiguration = configuration;
        this.id = configuration.getNodeId();
        this.currentTerm = 0;
        this.votedFor = 0;
        this.raftLog = raftLog;
        this.raftTransport = raftTransport;
        this.electionTimeoutScheduledExecutorService = electionTimeoutScheduledExecutorService;
        this.heartbeatstScheduledExecutorService = heartbeatstScheduledExecutorService;
        this.currentState = new FollowerState(this, raftTransport, electionTimeoutScheduledExecutorService);
        this.currentState.onEnterState();
    }


    public void becomeCandidateState() {
        transitionTo(new CandidateState(this, raftTransport, electionTimeoutScheduledExecutorService));
    }

    public void becomeLeader() {
        transitionTo(new LeaderState(this, raftTransport, heartbeatstScheduledExecutorService));
    }

    public void becomeFollower() {
        transitionTo(new FollowerState(this, raftTransport, electionTimeoutScheduledExecutorService));
    }

    public void transitionTo(RaftState newState) {
        this.currentState.onExitState();
        this.currentState = newState;
        this.currentState.onEnterState();
    }

    public int randomElectionTimeout() {
        return new Random().nextInt(raftConfiguration.getElectionTimeout()) + raftConfiguration.getElectionTimeout();
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getCurrentTerm() {
        return currentTerm;
    }


    public void setCurrentTerm(int currentTerm) {
        this.currentTerm = currentTerm;
    }


    public int getVotedFor() {
        return votedFor;
    }


    public void setVotedFor(int votedFor) {
        this.votedFor = votedFor;
    }


    public RaftState getCurrentState() {
        return currentState;
    }


    public void setCurrentState(RaftState currentState) {
        this.currentState = currentState;
    }


    public IRaftLog getRaftLog() {
        return raftLog;
    }


    public void setRaftLog(IRaftLog raftLog) {
        this.raftLog = raftLog;
    }


    public RaftConfiguration getRaftConfiguration() {
        return raftConfiguration;
    }


    public void setRaftConfiguration(RaftConfiguration raftConfiguration) {
        this.raftConfiguration = raftConfiguration;
    }


    public RaftTransport getRaftTransport() {
        return raftTransport;
    }


    public void setRaftTransport(RaftTransport raftTransport) {
        this.raftTransport = raftTransport;
    }


    public ScheduledExecutorService getSelectionTimeoutScheduledExecutorService() {
        return electionTimeoutScheduledExecutorService;
    }


    public void setSelectionTimeoutScheduledExecutorService(
            ScheduledExecutorService selectionTimeoutScheduledExecutorService) {
        this.electionTimeoutScheduledExecutorService = selectionTimeoutScheduledExecutorService;
    }


    public ScheduledExecutorService getHeartbeatstScheduledExecutorService() {
        return heartbeatstScheduledExecutorService;
    }


    public void setHeartbeatstScheduledExecutorService(ScheduledExecutorService heartbeatstScheduledExecutorService) {
        this.heartbeatstScheduledExecutorService = heartbeatstScheduledExecutorService;
    }

    
   
    

}
