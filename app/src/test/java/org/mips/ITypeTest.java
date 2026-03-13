package org.mips;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mips.Arithmetic.BinaryOperations;
import org.mips.Instructions.Instruction;
import org.mips.Registers.RegisterFile;

public class ITypeTest {
    @Test
    void addiExecutes() throws Exception {
        RegisterFile registerFile = new RegisterFile(4, 32);
        registerFile.getRegister(1)
                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(), 5));

        Instruction instruction = Instruction.of(1, "addi r3,r1,2");
        instruction.execute(registerFile);

        assertEquals(7, registerFile.getRegister(3).getDecimal());
        assertEquals("r3 <- r1 + 2", instruction.toString());
        assertEquals(1, instruction.getCycles());
    }

    @Test
    void subiExecutes() throws Exception {
        RegisterFile registerFile = new RegisterFile(4, 32);
        registerFile.getRegister(1)
                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(), 5));

        Instruction instruction = Instruction.of(1, "subi r3,r1,2");
        instruction.execute(registerFile);

        assertEquals(3, registerFile.getRegister(3).getDecimal());
        assertEquals("r3 <- r1 - 2", instruction.toString());
        assertEquals(1, instruction.getCycles());
    }

    @Test
    void muliExecutesAndUsesFourCycles() throws Exception {
        RegisterFile registerFile = new RegisterFile(4, 32);
        registerFile.getRegister(1)
                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(), 5));

        Instruction instruction = Instruction.of(1, "muli r3,r1,2");
        instruction.execute(registerFile);

        assertEquals(10, registerFile.getRegister(3).getDecimal());
        assertEquals("r3 <- r1 * 2", instruction.toString());
        assertEquals(4, instruction.getCycles());
    }

    @Test
    void diviExecutesAndUsesFourCycles() throws Exception {
        RegisterFile registerFile = new RegisterFile(4, 32);
        registerFile.getRegister(1)
                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(), 32));

        Instruction instruction = Instruction.of(1, "divi r3,r1,2");
        instruction.execute(registerFile);

        assertEquals(16, registerFile.getRegister(3).getDecimal());
        assertEquals("r3 <- r1 / 2", instruction.toString());
        assertEquals(4, instruction.getCycles());
    }

    @Test
    void operandRegistersAreDefensiveCopies() throws Exception {
        Instruction instruction = Instruction.of(1, "addi r3,r1,2");

        int[] operandRegisters = instruction.getOperandRegisters();
        operandRegisters[0] = 99;

        assertArrayEquals(new int[] { 3, 1 }, instruction.getOperandRegisters());
    }

    @Test
    void executeRejectsInvalidRegisterNumbers() throws Exception {
        RegisterFile registerFile = new RegisterFile(4, 32);
        Instruction instruction = Instruction.of(1, "addi r4,r1,2");

        Exception exception = assertThrows(Exception.class, () -> instruction.execute(registerFile));
        assertEquals("invalid register number", exception.getMessage());
    }

    @Test
    void getBitEncodedFormReflectsCurrentBehavior() throws Exception {
        RegisterFile registerFile = new RegisterFile(4, 32);
        registerFile.getRegister(1)
                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(), 5));

        Instruction instruction = Instruction.of(1, "addi r3,r1,2");
        instruction.execute(registerFile);

        assertEquals("000101 00011000010000000000000010", instruction.getBitEncodedForm());
    }
}
