package org.mips;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mips.Instructions.IType;
import org.mips.Instructions.Instruction;
import org.mips.Instructions.RType;

public class InstructionTest {
    @Test
    void factoryCreatesRTypeForArithmeticAndPseudoInstructions() throws Exception {
        assertInstanceOf(RType.class, Instruction.of(1, "add r3,r1,r2"));
        assertInstanceOf(RType.class, Instruction.of(2, "mov r3,r1"));
    }

    @Test
    void factoryCreatesITypeForImmediateInstructions() throws Exception {
        assertInstanceOf(IType.class, Instruction.of(1, "addi r3,r1,2"));
    }

    @Test
    void factoryPreservesInstructionMetadata() throws Exception {
        Instruction instruction = Instruction.of(7, "sub r3,r1,r2");

        assertEquals(7, instruction.getInstructionId());
        assertEquals("sub r3,r1,r2", instruction.getInstructionLiteral());
    }

    @Test
    void factoryRejectsMalformedAndUnsupportedInstructions() {
        Exception malformed = assertThrows(Exception.class, () -> Instruction.of(1, "add"));
        Exception unsupported = assertThrows(Exception.class, () -> Instruction.of(1, "noop r1,r2,r3"));

        assertEquals("wrong instruction format: expected '<opcode> <operands>'", malformed.getMessage());
        assertEquals("unsupported opcode: noop", unsupported.getMessage());
    }
}