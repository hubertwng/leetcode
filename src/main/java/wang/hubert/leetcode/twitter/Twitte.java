package wang.hubert.leetcode.twitter;

public class Twitte {
    /**
     * 用户id
     */
    private Integer userId;


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    /**
     * 发布的时间戳
     */
    private Integer timestamp;


    public Integer getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * 内容
     */
    private String content;


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public Twitte(Integer userId, String content) {
        this.userId = userId;
        this.content = content;
    }
    
    
}
