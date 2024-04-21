package wang.hubert.leetcode.alg;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Assert;
import org.junit.Test;
public class Alg394Test {

    @Test
    public void testDecodeString() {
        // case1
        String case1 = "10[a]2[bc]";
        String excepted1 = "aaaaaaaaaabcbc";
        Assert.assertEquals(excepted1, Alg394.decodeString(case1));

        String case2 = "3[a2[c]]";
        String excepted2 = "accaccacc";
        Assert.assertEquals(excepted2, Alg394.decodeString(case2));

        String case3 = "3[a10[c]]";
        String excepted3 = "accccccccccaccccccccccacccccccccc";
        Assert.assertEquals(excepted3, Alg394.decodeString(case3));



    }
}
