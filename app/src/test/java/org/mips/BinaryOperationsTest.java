package org.mips;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mips.Arithmetic.BinaryOperations;
import org.mips.Arithmetic.Utils;

public class BinaryOperationsTest {
    @Test
    void addSupportsPositiveAndNegativeInputs() throws Exception {
        assertArrayEquals(Utils.decimalToBooleanArray(6, 5),
                BinaryOperations.Add(Utils.decimalToBooleanArray(4, 5), Utils.decimalToBooleanArray(2, 5)));
        assertArrayEquals(Utils.decimalToBooleanArray(1, 5),
                BinaryOperations.Add(Utils.decimalToBooleanArray(-1, 5), Utils.decimalToBooleanArray(2, 5)));
    }

    @Test
    void addRejectsOverflowAndUnderflow() {
        assertThrows(Exception.class,
                () -> BinaryOperations.Add(Utils.decimalToBooleanArray(2, 3), Utils.decimalToBooleanArray(2, 3)));
        assertThrows(Exception.class,
                () -> BinaryOperations.Add(Utils.decimalToBooleanArray(-3, 3), Utils.decimalToBooleanArray(-3, 3)));
    }

    @Test
    void addRejectsMismatchedLengths() {
        Exception exception = assertThrows(Exception.class,
                () -> BinaryOperations.Add(Utils.decimalToBooleanArray(1, 4), Utils.decimalToBooleanArray(1, 5)));

        assertEquals(
                "register arrays must be of equal length.\ncannot add register of 4 length with another 5 length.",
                exception.getMessage());
    }

    @Test
    void subtractSupportsPositiveAndNegativeResults() throws Exception {
        assertArrayEquals(Utils.decimalToBooleanArray(4, 5),
                BinaryOperations.Subtract(Utils.decimalToBooleanArray(6, 5), Utils.decimalToBooleanArray(2, 5)));
        assertArrayEquals(Utils.decimalToBooleanArray(-1, 5),
                BinaryOperations.Subtract(Utils.decimalToBooleanArray(1, 5), Utils.decimalToBooleanArray(2, 5)));
    }

    @Test
    void subtractRejectsOverflowAndUnderflow() {
        assertThrows(Exception.class,
                () -> BinaryOperations.Subtract(Utils.decimalToBooleanArray(3, 3), Utils.decimalToBooleanArray(-2, 3)));
        assertThrows(Exception.class,
                () -> BinaryOperations.Subtract(Utils.decimalToBooleanArray(-3, 3), Utils.decimalToBooleanArray(2, 3)));
    }

    @Test
    void multiplyHandlesSignsAndZero() throws Exception {
        assertArrayEquals(Utils.decimalToBooleanArray(6, 5),
                BinaryOperations.Multiply(Utils.decimalToBooleanArray(3, 5), Utils.decimalToBooleanArray(2, 5)));
        assertArrayEquals(Utils.decimalToBooleanArray(-6, 5),
                BinaryOperations.Multiply(Utils.decimalToBooleanArray(-3, 5), Utils.decimalToBooleanArray(2, 5)));
        assertArrayEquals(Utils.decimalToBooleanArray(6, 5),
                BinaryOperations.Multiply(Utils.decimalToBooleanArray(-2, 5), Utils.decimalToBooleanArray(-3, 5)));
        assertArrayEquals(Utils.decimalToBooleanArray(0, 5),
                BinaryOperations.Multiply(Utils.decimalToBooleanArray(0, 5), Utils.decimalToBooleanArray(5, 5)));
    }

    @Test
    void divideHandlesSignsAndRejectsDivisionByZero() throws Exception {
        assertArrayEquals(Utils.decimalToBooleanArray(3, 5),
                BinaryOperations.Divide(Utils.decimalToBooleanArray(6, 5), Utils.decimalToBooleanArray(2, 5)));
        assertArrayEquals(Utils.decimalToBooleanArray(-3, 5),
                BinaryOperations.Divide(Utils.decimalToBooleanArray(-6, 5), Utils.decimalToBooleanArray(2, 5)));
        assertArrayEquals(Utils.decimalToBooleanArray(3, 5),
                BinaryOperations.Divide(Utils.decimalToBooleanArray(-6, 5), Utils.decimalToBooleanArray(-2, 5)));
        assertThrows(Exception.class,
                () -> BinaryOperations.Divide(Utils.decimalToBooleanArray(5, 5), Utils.decimalToBooleanArray(0, 5)));
    }

    @Test
    void immediateHelpersDelegateToCoreOperations() throws Exception {
        assertArrayEquals(Utils.decimalToBooleanArray(7, 5),
                BinaryOperations.AddI(Utils.decimalToBooleanArray(5, 5), 2));
        assertArrayEquals(Utils.decimalToBooleanArray(3, 5),
                BinaryOperations.SubI(Utils.decimalToBooleanArray(5, 5), 2));
        assertArrayEquals(Utils.decimalToBooleanArray(10, 5),
                BinaryOperations.MulI(Utils.decimalToBooleanArray(5, 5), 2));
        assertArrayEquals(Utils.decimalToBooleanArray(4, 5),
                BinaryOperations.DivI(Utils.decimalToBooleanArray(8, 5), 2));
    }
}
