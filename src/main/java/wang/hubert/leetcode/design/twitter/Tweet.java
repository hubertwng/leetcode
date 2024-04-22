package wang.hubert.leetcode.design.twitter;

public class Tweet {
    /**
     * 用户id
     */
    private Integer Id;

    /**
     * 发布的时间戳
     */
    private Integer timestamp;

    /**
     * 内容
     */
    private String content;


    private Integer userId;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Tweet(Integer userId, String content, TweetIdGenerator tweetIdGenerator, TweetTimestampGenerator timestampGenerator) {
        this.content = content;
        this.userId = userId;
        this.Id = tweetIdGenerator.nextId();
        this.timestamp = timestampGenerator.timestamp();
    }

    
    
    
}
