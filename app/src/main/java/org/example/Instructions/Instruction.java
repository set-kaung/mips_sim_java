package org.example.Instructions;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.google.common.base.Splitter;

import org.example.Registers.RegisterFile;

public abstract class Instruction {
    private static final Set<String> PSEUDO_OPCODES = Set.of("mov");
    private static final Set<String> RTYPE_OPCODES = Set.of("add", "sub", "mul", "div");
    private static final Set<String> ITYPE_OPCODES = Set.of("addi", "subi", "muli", "divi");
    private static final Splitter SPACE_SPLITTER = Splitter.on(' ').limit(2);
    private static final int INSTRUCTION_PART_COUNT = 2;

    protected int insID;
    protected String insLiteral;
    protected String opcode;
    protected int[] operandRegisterIDs;

    public static Instruction of(int insID, String insLiteral) throws Exception {
        List<String> parts = SPACE_SPLITTER.splitToList(Objects.requireNonNull(insLiteral));
        if (parts.size() != INSTRUCTION_PART_COUNT) {
            throw new Exception("wrong instruction format: expected '<opcode> <operands>'");
        }
        String opcode = parts.get(0);
        String operands = parts.get(1);
        if (RTYPE_OPCODES.contains(opcode)) {
            return new RType(insID, insLiteral, opcode, operands);
        } else if (ITYPE_OPCODES.contains(opcode)) {
            return new IType(insID, insLiteral, opcode, operands);
        } else if (PSEUDO_OPCODES.contains(opcode)) {

            return new RType(insID, insLiteral, "add", operands + ",r0");
        }
        throw new Exception("unsupported opcode: " + opcode);
    }

    public int getInstructionId() {
        return insID;
    }

    public String getInstructionLiteral() {
        return insLiteral;
    }

    public abstract int[] getOperandRegisters();

    public abstract void execute(RegisterFile rf) throws Exception;
}
