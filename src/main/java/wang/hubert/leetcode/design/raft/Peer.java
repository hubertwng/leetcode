package wang.hubert.leetcode.design.raft;

public class Peer {
    
    private int id;

    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Peer(int id, String address) {
        this.id = id;
        this.address = address;
    }

    

}
