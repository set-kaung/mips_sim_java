package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BinaryOperationsTest {
    @Test
    public void addTest() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(4, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Add(A, B);
            boolean[] want = Utils.decimalToBooleanArray(6, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void addTest2() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(-1, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Add(A, B);
            boolean[] want = Utils.decimalToBooleanArray(1, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void addTest3() {
        int size = 3;
        boolean[] A = Utils.decimalToBooleanArray(2, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        assertThrows(Exception.class, () -> {
            BinaryOperations.Add(A, B);
        });
    }

    @Test
    public void addTest4() {
        int size = 3;
        boolean[] A = Utils.decimalToBooleanArray(-3, size);
        boolean[] B = Utils.decimalToBooleanArray(-3, size);
        assertThrows(Exception.class, () -> {
            BinaryOperations.Add(A, B);
        });
    }

    @Test
    public void subtractTest() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(6, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Subtract(A, B);
            boolean[] want = Utils.decimalToBooleanArray(4, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void subtractTest2() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(1, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Subtract(A, B);
            boolean[] want = Utils.decimalToBooleanArray(-1, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void subtractTest3() {
        int size = 3;
        boolean[] A = Utils.decimalToBooleanArray(3, size);
        boolean[] B = Utils.decimalToBooleanArray(-2, size);
        assertThrows(Exception.class, () -> {
            BinaryOperations.Subtract(A, B);
        });
    }

    @Test
    public void subtractTest4() {
        int size = 3;
        boolean[] A = Utils.decimalToBooleanArray(-3, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        assertThrows(Exception.class, () -> {
            BinaryOperations.Subtract(A, B);
        });
    }

    @Test
    public void multiplyTest() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(3, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Multiply(A, B);
            boolean[] want = Utils.decimalToBooleanArray(6, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void multiplyTest2() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(-3, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Multiply(A, B);
            boolean[] want = Utils.decimalToBooleanArray(-6, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void multiplyTest3() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(-2, size);
        boolean[] B = Utils.decimalToBooleanArray(-3, size);
        try {
            boolean[] got = BinaryOperations.Multiply(A, B);
            boolean[] want = Utils.decimalToBooleanArray(6, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void multiplyTest4() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(0, size);
        boolean[] B = Utils.decimalToBooleanArray(5, size);
        try {
            boolean[] got = BinaryOperations.Multiply(A, B);
            boolean[] want = Utils.decimalToBooleanArray(0, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void divideTest() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(6, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Divide(A, B);
            boolean[] want = Utils.decimalToBooleanArray(3, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void divideTest2() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(-6, size);
        boolean[] B = Utils.decimalToBooleanArray(2, size);
        try {
            boolean[] got = BinaryOperations.Divide(A, B);
            boolean[] want = Utils.decimalToBooleanArray(-3, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void divideTest3() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(-6, size);
        boolean[] B = Utils.decimalToBooleanArray(-2, size);
        try {
            boolean[] got = BinaryOperations.Divide(A, B);
            boolean[] want = Utils.decimalToBooleanArray(3, size);
            assertArrayEquals(got, want);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void divideTest4() {
        int size = 5;
        boolean[] A = Utils.decimalToBooleanArray(5, size);
        boolean[] B = Utils.decimalToBooleanArray(0, size);
        assertThrows(Exception.class, () -> {
            BinaryOperations.Divide(A, B);
        });
    }
}
