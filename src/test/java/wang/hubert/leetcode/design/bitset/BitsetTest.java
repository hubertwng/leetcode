package wang.hubert.leetcode.design.bitset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BitsetTest {

    @Test
    public void testFix() {
        Bitset bitset = new Bitset(5);
        bitset.fix(3);
        assertTrue(bitset.one());
        assertFalse(bitset.all());
        assertEquals(1, bitset.count());
        assertEquals("00010", bitset.toString());


        bitset = new Bitset(33);
        bitset.fix(3);
        assertTrue(bitset.one());
        assertFalse(bitset.all());
        assertEquals(1, bitset.count());


        bitset = new Bitset(2);

        bitset.flip();
        bitset.unfix(1);
        assertFalse(bitset.all());
        bitset.fix(1);
        bitset.fix(1);
        bitset.unfix(1);
        assertFalse(bitset.all());
        assertEquals(1, bitset.count());
        assertEquals("10", bitset.toString());
        assertEquals("10", bitset.toString());
        assertEquals("10", bitset.toString());
        bitset.unfix(0);
        bitset.flip();
        assertTrue(bitset.all());
        bitset.unfix(0);
        assertTrue(bitset.one());
        assertTrue(bitset.one());
        assertFalse(bitset.all());
        bitset.fix(0);
        bitset.unfix(0);

    }





}
