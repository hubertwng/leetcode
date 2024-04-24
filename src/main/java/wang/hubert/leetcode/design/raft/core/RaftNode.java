package wang.hubert.leetcode.design.raft.core;

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
    private RaftTransport messageSender;

    private RaftConfiguration raftConfiguration;


    public RaftNode(IRaftLog raftLog, RaftTransport messageSender, RaftConfiguration configuration) {
        this.id = configuration.getNodeId();
        this.currentTerm = 0;
        this.votedFor = 0;
        this.raftLog = raftLog;
        this.messageSender = messageSender;
        this.currentState = new FollowerState(this, messageSender);
        this.currentState.onEnterState();
    }


    public void becomeCandidateState() {
        transitionTo(new CandidateState(this, messageSender));
    }

    public void becomeLeader() {
        transitionTo(new LeaderState(this, messageSender));
    }

    public void becomeFollower() {
        transitionTo(new FollowerState(this, messageSender));
    }


    public void transitionTo(RaftState newState) {
        this.currentState.onExitState();
        this.currentState = newState;
        this.currentState.onEnterState();
    }



    public RaftState getCurrentState() {
        return currentState;
    }




    public IRaftLog getRaftLog() {
        return raftLog;
    }


    public RaftTransport getMessageSender() {
        return messageSender;
    }

    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCurrentState(RaftState currentState) {
        this.currentState = currentState;
    }


    public void setRaftLog(IRaftLog raftLog) {
        this.raftLog = raftLog;
    }


    public void setMessageSender(RaftTransport messageSender) {
        this.messageSender = messageSender;
    }


    public RaftConfiguration getRaftConfiguration() {
        return raftConfiguration;
    }





    public void setRaftConfiguration(RaftConfiguration raftConfiguration) {
        this.raftConfiguration = raftConfiguration;
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
    
    
    

}
