package org.example;

import org.junit.jupiter.api.Test;
import org.mips.Arithmetic.BinaryOperations;
import org.mips.Instructions.Instruction;
import org.mips.Registers.RegisterFile;

import static org.junit.jupiter.api.Assertions.*;

public class RTypeTest {
    @Test
    void testAdd() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        var r2 = rf.getRegister(2);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 5));
            r2.setRegisterArray(BinaryOperations.AddI(r2.getRegisterArray(), 2));

            // 1 add r3,r1,r2
            Instruction ins = Instruction.of(1, "add r3,r1,r2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 7);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void testSub() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        var r2 = rf.getRegister(2);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 5));
            r2.setRegisterArray(BinaryOperations.AddI(r2.getRegisterArray(), 2));

            // 1 add r3,r1,r2
            Instruction ins = Instruction.of(1, "sub r3,r1,r2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void testMul() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        var r2 = rf.getRegister(2);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 5));
            r2.setRegisterArray(BinaryOperations.AddI(r2.getRegisterArray(), 2));

            // 1 add r3,r1,r2
            Instruction ins = Instruction.of(1, "mul r3,r1,r2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 10);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void testDiv() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        var r2 = rf.getRegister(2);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 32));
            r2.setRegisterArray(BinaryOperations.AddI(r2.getRegisterArray(), 2));

            // 1 add r3,r1,r2
            Instruction ins = Instruction.of(1, "div r3,r1,r2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 16);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
