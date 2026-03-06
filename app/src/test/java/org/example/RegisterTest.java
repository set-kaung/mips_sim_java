package org.example;

import org.example.Registers.Register;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class RegisterTest {
    @Test
    void testsetRegisterArrayValue() {
        boolean[] valNeg67 = new boolean[32];
        Arrays.fill(valNeg67, true);
        valNeg67[25] = false;
        valNeg67[30] = false;

        Register reg1 = new Register(1, 32);
        reg1.setRegisterArray(valNeg67);

        assertArrayEquals(new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true,
                true,
                true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, true, false,
                true }, reg1.getRegisterArray());
        assertEquals(-67, reg1.getDecimal());
    }
}
