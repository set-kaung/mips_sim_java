package org.mips;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;
import org.mips.Arithmetic.Utils;
import org.mips.Registers.Register;
import org.mips.Registers.RegisterFile;

public class RegisterTest {
    @Test
    void registerStoresBitArrayAndSignedDecimal() {
        Register register = new Register(1, 32);
        boolean[] value = Utils.decimalToBooleanArray(-67, 32);

        register.setRegisterArray(value);

        assertArrayEquals(value, register.getRegisterArray());
        assertEquals(-67, register.getDecimal());
    }

    @Test
    void registerZeroIgnoresWrites() {
        Register registerZero = new Register(0, 32);

        registerZero.setRegisterArray(Utils.decimalToBooleanArray(15, 32));

        assertArrayEquals(new boolean[32], registerZero.getRegisterArray());
        assertEquals(0, registerZero.getDecimal());
    }

    @Test
    void getRegisterArrayReturnsClone() {
        Register register = new Register(1, 8);
        register.setRegisterArray(Utils.decimalToBooleanArray(5, 8));

        boolean[] bits = register.getRegisterArray();
        bits[7] = false;

        assertArrayEquals(Utils.decimalToBooleanArray(5, 8), register.getRegisterArray());
    }

    @Test
    void registerFileSetRegisterStoresClone() {
        RegisterFile registerFile = new RegisterFile(4, 8);
        Register source = new Register(2, 8);
        source.setRegisterArray(Utils.decimalToBooleanArray(3, 8));

        registerFile.setRegister(1, source);
        Register stored = registerFile.getRegister(1);
        source.setRegisterArray(Utils.decimalToBooleanArray(7, 8));

        assertNotSame(source, stored);
        assertEquals(3, stored.getDecimal());
    }
}
