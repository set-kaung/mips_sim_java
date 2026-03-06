package org.example;

import org.example.Arithmetic.Utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class UtilsTest {
    @Test
    void positiveTwosComplement() {
        boolean[] val67 = new boolean[32];
        val67[25] = true;
        val67[30] = true;
        val67[31] = true;
        long result = Utils.bitArrayToTwosComplement(val67);
        assertEquals(67, result);
    }

    @Test
    void negativeTwosComplement() {
        boolean[] valNeg67 = new boolean[32];
        Arrays.fill(valNeg67, true);
        valNeg67[25] = false;
        valNeg67[30] = false;
        long result = Utils.bitArrayToTwosComplement(valNeg67);
        assertEquals(-67, result);
    }

    @Test
    void positiveDeciamlToBArray() {
        int size = 4;
        boolean[] res = Utils.decimalToBooleanArray(4, size);
        assertArrayEquals(new boolean[] { false, true, false, false }, res);
    }

    @Test
    void positiveDeciamlToBArray2() {
        int size = 5;
        boolean[] res = Utils.decimalToBooleanArray(7, size);
        assertArrayEquals(new boolean[] { false, false, true, true, true }, res);
    }

    @Test
    void negativeDeciamlToBArray() {
        int size = 5;
        boolean[] res = Utils.decimalToBooleanArray(-5, size);
        assertArrayEquals(new boolean[] { true, true, false, true, true }, res);
    }
}
