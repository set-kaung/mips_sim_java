package org.example.Arithmetic;

public class Utils {

    public static long bitArrayToTwosComplement(boolean[] registerArray) {
        boolean[] tempRegArr = registerArray.clone();
        long result = 0;
        int l = tempRegArr.length - 1;
        if (tempRegArr[0]) {
            for (int i = 0; i < tempRegArr.length; i++) {
                tempRegArr[i] = !tempRegArr[i];
            }
            boolean carry = true;
            for (int i = tempRegArr.length - 1; i >= 0; i--) {
                boolean cValue = tempRegArr[i];
                tempRegArr[i] = carry ^ cValue;
                carry = cValue && carry;
                long nValue = tempRegArr[i] ? 1 : 0;
                result += nValue << (l - i);
            }
            return -result;
        } else {
            for (int i = tempRegArr.length - 1; i >= 0; i--) {
                long nValue = tempRegArr[i] ? 1 : 0;
                result += nValue << (l - i);
            }
            return result;
        }
    }

    public static long powerN(long number, int power) {
        long res = 1;
        long sq = number;
        int p = power;
        while (p > 0) {
            if (p % 2 != 0) {
                res *= sq;
            }
            sq = sq * sq;
            p /= 2;
        }
        return res;
    }

    public static boolean[] decimalToBooleanArray(long decimal, int size) {
        boolean[] res = new boolean[size];
        if (decimal == 0) {
            return res;
        }
        long original = decimal;
        long absDecimal = Math.abs(decimal);
        int idx = size - 1;
        while (absDecimal != 0) {
            res[idx] = absDecimal % 2 > 0;
            absDecimal /= 2;
            idx -= 1;
        }
        if (original < 0) {
            for (int i = 0; i < size; i++) {
                res[i] = !res[i];
            }
            boolean carry = true;
            for (int i = size - 1; i >= 0; i--) {
                boolean nVal = res[i] ^ carry;
                carry = res[i] && carry;
                res[i] = nVal;
            }
        }
        return res;
    }

}
