package wang.hubert.leetcode.design.bitset;

public class Bitset {
    
    private byte [] bytes;
    private int size;

    public Bitset(int size) {
        this.size = size;
        this.bytes = new byte[(size >> 3) + 1];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)0b00000000;
        }
    }
    
    public void fix(int idx) {
        int solt = (idx >> 3);
        int soltIndex = idx % 8;
        byte mask = (byte) (1 << soltIndex);
        bytes[solt] = (byte) (bytes[solt] | mask);
    }
    
    public void unfix(int idx) {
        int solt = (idx >> 3);
        int soltIndex = idx % 8;
        byte mask = (byte) (1 << soltIndex);
        bytes[solt] = (byte) (bytes[solt] & ~mask);
    }
    
    public void flip() {
        int remainSize = size;
        for (int i = 0; i < bytes.length;  i++) {
            for (int j = 0; j < 8 && remainSize > 0; j++) {
                byte mask = (byte)(1 << j);
                bytes[i] = (byte)(bytes[i] ^ mask);
                remainSize--;
            }
        }
    }
    
    public boolean all() {
        int remainSize = size;
        for (int i = 0; i < bytes.length; i++) {
           for (int j = 0; j < 8 && remainSize > 0; j++) {
                byte mask = (byte)(1 << j);
                if ((bytes[i] & mask) == 0) {
                    return false;
                }
                remainSize--;
           }
        }
        return true;
    }
    
    public boolean one() {
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != 0) {
                return true;
            }
        }
        return false;
    }
    
    public int count() {
        int count = 0;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                if(((1 << j) & bytes[i]) != 0) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int count = size;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8 && count > 0 ; j++) {
                if(((1 << j) & bytes[i]) != 0) {
                    sb.append(1);
                } else {
                    sb.append(0);
                }
                count--;
            }
        }
        return sb.toString();
    }
}
