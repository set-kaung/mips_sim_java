package org.example.Instructions;

import org.example.Registers.RegisterFile;

public abstract class Instruction {
    protected int insID;
    protected String insLiteral;
    protected String opcode;
    protected int[] operandRegisterIDs;

    public int getInstructionId() {
        return insID;
    }

    public String getInstructionLiteral() {
        return insLiteral;
    }

    public abstract void execute(RegisterFile rf) throws Exception;
}
