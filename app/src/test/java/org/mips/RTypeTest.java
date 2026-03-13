package org.mips;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mips.Arithmetic.BinaryOperations;
import org.mips.Instructions.Instruction;
import org.mips.Registers.RegisterFile;

public class RTypeTest {
        @Test
        void addExecutesAndEncodes() throws Exception {
                RegisterFile registerFile = new RegisterFile(4, 32);
                registerFile.getRegister(1)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(),
                                                5));
                registerFile.getRegister(2)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(2).getRegisterArray(),
                                                2));

                Instruction instruction = Instruction.of(1, "add r3,r1,r2");
                instruction.execute(registerFile);

                assertEquals(7, registerFile.getRegister(3).getDecimal());
                assertEquals("r3 <- r1 + r2", instruction.toString());
                assertEquals(1, instruction.getCycles());
                assertEquals("000001 00011000010001000000000000", instruction.getBitEncodedForm());
        }

        @Test
        void subExecutes() throws Exception {
                RegisterFile registerFile = new RegisterFile(4, 32);
                registerFile.getRegister(1)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(),
                                                5));
                registerFile.getRegister(2)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(2).getRegisterArray(),
                                                2));

                Instruction instruction = Instruction.of(1, "sub r3,r1,r2");
                instruction.execute(registerFile);

                assertEquals(3, registerFile.getRegister(3).getDecimal());
                assertEquals("r3 <- r1 - r2", instruction.toString());
                assertEquals(1, instruction.getCycles());
        }

        @Test
        void mulExecutesAndUsesFourCycles() throws Exception {
                RegisterFile registerFile = new RegisterFile(4, 32);
                registerFile.getRegister(1)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(),
                                                5));
                registerFile.getRegister(2)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(2).getRegisterArray(),
                                                2));

                Instruction instruction = Instruction.of(1, "mul r3,r1,r2");
                instruction.execute(registerFile);

                assertEquals(10, registerFile.getRegister(3).getDecimal());
                assertEquals("r3 <- r1 * r2", instruction.toString());
                assertEquals(4, instruction.getCycles());
        }

        @Test
        void divExecutesAndUsesFourCycles() throws Exception {
                RegisterFile registerFile = new RegisterFile(4, 32);
                registerFile.getRegister(1)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(),
                                                32));
                registerFile.getRegister(2)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(2).getRegisterArray(),
                                                2));

                Instruction instruction = Instruction.of(1, "div r3,r1,r2");
                instruction.execute(registerFile);

                assertEquals(16, registerFile.getRegister(3).getDecimal());
                assertEquals("r3 <- r1 / r2", instruction.toString());
                assertEquals(4, instruction.getCycles());
        }

        @Test
        void movPseudoInstructionExecutesViaAddWithR0() throws Exception {
                RegisterFile registerFile = new RegisterFile(4, 32);
                registerFile.getRegister(1)
                                .setRegisterArray(BinaryOperations.AddI(registerFile.getRegister(1).getRegisterArray(),
                                                28));

                Instruction instruction = Instruction.of(6, "mov r3,r1");
                instruction.execute(registerFile);

                assertEquals(28, registerFile.getRegister(3).getDecimal());
                assertEquals("mov r3,r1", instruction.getInstructionLiteral());
                assertArrayEquals(new int[] { 3, 1, 0 }, instruction.getOperandRegisters());
                assertEquals("r3 <- r1", instruction.toString());
                assertEquals(1, instruction.getCycles());
        }

        @Test
        void operandRegistersAreDefensiveCopies() throws Exception {
                Instruction instruction = Instruction.of(1, "add r3,r1,r2");

                int[] operandRegisters = instruction.getOperandRegisters();
                operandRegisters[0] = 99;

                assertArrayEquals(new int[] { 3, 1, 2 }, instruction.getOperandRegisters());
        }

        @Test
        void executeRejectsInvalidRegisterNumbers() throws Exception {
                RegisterFile registerFile = new RegisterFile(4, 32);
                Instruction instruction = Instruction.of(1, "add r4,r1,r2");

                Exception exception = assertThrows(Exception.class, () -> instruction.execute(registerFile));
                assertEquals("invalid register number", exception.getMessage());
        }
}
