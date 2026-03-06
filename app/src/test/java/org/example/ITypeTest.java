package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.Arithmetic.BinaryOperations;
import org.example.Instructions.Instruction;
import org.example.Registers.RegisterFile;

public class ITypeTest {

    @Test
    void testAddi() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 5));

            // 1 addi r3,r1,#2
            Instruction ins = Instruction.of(1, "addi r3,r1,#2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 7);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void testSubi() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 5));

            // 1 subi r3,r1,#2
            Instruction ins = Instruction.of(1, "subi r3,r1,#2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void testMuli() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 5));

            // 1 muli r3,r1,#2
            Instruction ins = Instruction.of(1, "muli r3,r1,#2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 10);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void testDivi() {
        RegisterFile rf = new RegisterFile(4, 32);
        var r1 = rf.getRegister(1);
        try {
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 32));

            // 1 divi r3,r1,#2
            Instruction ins = Instruction.of(1, "divi r3,r1,#2");
            ins.execute(rf);

            assertEquals(rf.getRegister(3).getDecimal(), 16);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
