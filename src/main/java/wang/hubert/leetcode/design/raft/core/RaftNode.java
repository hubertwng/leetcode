package wang.hubert.leetcode.design.raft.core;

public class RaftNode {

    private String id;

    /**
     * 当前任期
     */
    private int ccurrrentTerm;
    
    /**
     * 投给谁了
     */
    private String votedFor;

    /**
     * 当前状态
     */
    private IState currentState;

    /**
     * 日志记录器
     */
    private IRaftLog raftLog;

    /**
     * 消息发送器
     */
    private ImessageSender messageSender;


    public RaftNode(IRaftLog raftLog, ImessageSender messageSender, RaftConfiguration configuration) {
        this.id = configuration.getNodeId();
        this.ccurrrentTerm = 0;
        this.votedFor = "";
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


    public void transitionTo(IState newState) {
        this.currentState.onExitState();
        this.currentState = newState;
        this.currentState.onEnterState();
    }


    public int getCcurrrentTerm() {
        return ccurrrentTerm;
    }


    public String getVotedFor() {
        return votedFor;
    }


    public void setVotedFor(String votedFor) {
        this.votedFor = votedFor;
    }


    public IState getCurrentState() {
        return currentState;
    }




    public IRaftLog getRaftLog() {
        return raftLog;
    }


    public ImessageSender getMessageSender() {
        return messageSender;
    }
    
    public String getId() {
        return this.id;
    }
    

}
