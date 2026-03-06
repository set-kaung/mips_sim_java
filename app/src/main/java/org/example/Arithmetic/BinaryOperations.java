package org.example.Arithmetic;

import java.util.stream.IntStream;

public class BinaryOperations {
    public static boolean[] Add(boolean[] A, boolean[] B) throws Exception {
        if (A.length != B.length) {
            throw new Exception(String.format(
                    "register arrays must be of equal length.%ncannot add register of %d length with another %d length.",
                    A.length, B.length));
        }
        long AD = Utils.bitArrayToTwosComplement(A);
        long BD = Utils.bitArrayToTwosComplement(B);
        boolean[] res = new boolean[A.length];
        boolean carry = false;
        for (int i = A.length - 1; i >= 0; i--) {
            res[i] = A[i] ^ B[i] ^ carry;
            carry = (A[i] && B[i]) || (B[i] && carry) || (A[i] && carry);
        }
        long RD = Utils.bitArrayToTwosComplement(res);
        if (AD > 0 && BD > 0 && RD < 0) {
            throw new Exception("binary overflowed");
        } else if (AD < 0 && BD < 0 && RD > 0) {
            throw new Exception("binary underflowed");
        }

        return res;
    }

    public static boolean[] Subtract(boolean[] A, boolean[] B) throws Exception {
        boolean[] negB = negate(B);
        return Add(A, negB);
    }

    public static boolean[] Multiply(boolean[] A, boolean[] B) throws Exception {
        int size = A.length;

        boolean signA = A[0];
        boolean signB = B[0];
        boolean resultSign = signA ^ signB;

        boolean[] absA = signA ? negate(A) : A.clone();
        boolean[] absB = signB ? negate(B) : B.clone();

        boolean[] result = new boolean[size];
        for (int i = size - 1; i >= 0; i--) {
            if (absB[i]) {
                int shift = size - 1 - i;
                boolean[] shifted = new boolean[size];
                for (int j = size - 1 - shift; j >= 0; j--) {
                    shifted[j] = absA[j + shift];
                }
                result = Add(result, shifted);
            }
        }

        if (resultSign) {
            result = negate(result);
        }

        return result;
    }

    public static boolean[] Divide(boolean[] A, boolean[] B) throws Exception {
        int size = A.length;

        boolean allZero = IntStream.range(0, B.length).allMatch(i -> !B[i]);

        if (allZero) {
            throw new Exception("division by zero");
        }
        boolean signA = A[0];
        boolean signB = B[0];
        boolean resultSign = signA ^ signB;

        boolean[] absA = signA ? negate(A) : A.clone();
        boolean[] absB = signB ? negate(B) : B.clone();

        boolean[] quotient = new boolean[size];
        boolean[] remainder = new boolean[size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                remainder[j] = remainder[j + 1];
            }
            remainder[size - 1] = absA[i];

            if (greaterThanOrEqual(remainder, absB)) {
                remainder = Subtract(remainder, absB);
                quotient[i] = true;
            }
        }

        if (resultSign) {
            quotient = negate(quotient);
        }

        return quotient;
    }

    private static boolean greaterThanOrEqual(boolean[] A, boolean[] B) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] && !B[i])
                return true;
            if (!A[i] && B[i])
                return false;
        }
        return true; // equal
    }

    private static boolean[] negate(boolean[] arr) {
        boolean[] res = arr.clone();
        for (int i = 0; i < res.length; i++) {
            res[i] = !res[i];
        }
        boolean carry = true;
        for (int i = res.length - 1; i >= 0; i--) {
            boolean nVal = res[i] ^ carry;
            carry = res[i] && carry;
            res[i] = nVal;
        }
        return res;
    }

    public static boolean[] AddI(boolean[] A, long immediate) throws Exception {
        boolean[] B = Utils.decimalToBooleanArray(immediate, A.length);
        return Add(A, B);
    }

    public static boolean[] SubI(boolean[] A, long immediate) throws Exception {
        boolean[] B = Utils.decimalToBooleanArray(immediate, A.length);
        return Subtract(A, B);
    }

    public static boolean[] MulI(boolean[] A, long immediate) throws Exception {
        boolean[] B = Utils.decimalToBooleanArray(immediate, A.length);
        return Multiply(A, B);
    }

    public static boolean[] DivI(boolean[] A, long immediate) throws Exception {
        boolean[] B = Utils.decimalToBooleanArray(immediate, A.length);
        return Divide(A, B);
    }
}
