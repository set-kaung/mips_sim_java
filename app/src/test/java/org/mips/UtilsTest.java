package org.mips;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mips.Arithmetic.Utils;

public class UtilsTest {
    @Test
    void bitArrayToTwosComplementHandlesPositiveNegativeAndZero() {
        assertEquals(67, Utils.bitArrayToTwosComplement(Utils.decimalToBooleanArray(67, 32)));
        assertEquals(-67, Utils.bitArrayToTwosComplement(Utils.decimalToBooleanArray(-67, 32)));
        assertEquals(0, Utils.bitArrayToTwosComplement(new boolean[32]));
    }

    @Test
    void decimalToBooleanArrayHandlesPositiveNegativeAndZero() {
        assertArrayEquals(new boolean[] { false, true, false, false }, Utils.decimalToBooleanArray(4, 4));
        assertArrayEquals(new boolean[] { false, false, true, true, true }, Utils.decimalToBooleanArray(7, 5));
        assertArrayEquals(new boolean[] { true, true, false, true, true }, Utils.decimalToBooleanArray(-5, 5));
        assertArrayEquals(new boolean[5], Utils.decimalToBooleanArray(0, 5));
    }

    @Test
    void powerNComputesIntegerPowers() {
        assertEquals(1, Utils.powerN(7, 0));
        assertEquals(32, Utils.powerN(2, 5));
        assertEquals(81, Utils.powerN(3, 4));
    }

    @Test
    void bitStringFromDecimalWithSizeFormatsValuesAndRejectsOversizedInput() throws Exception {
        assertEquals("00010", Utils.bitStringFromDecimalWithSize(2, 5));
        assertEquals("00101", Utils.bitStringFromDecimalWithSize(5, 5));
        assertEquals("00111", Utils.bitStringFromDecimalWithSize(7, 5));
        assertEquals("01010", Utils.bitStringFromDecimalWithSize(10, 5));
        assertEquals("10000", Utils.bitStringFromDecimalWithSize(16, 5));

        Exception exception = assertThrows(Exception.class, () -> Utils.bitStringFromDecimalWithSize(32, 5));
        assertEquals("decimal too big for given size", exception.getMessage());
    }
}
