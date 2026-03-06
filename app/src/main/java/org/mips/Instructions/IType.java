package org.mips.Instructions;

import java.util.List;
import java.util.Objects;

import org.mips.Arithmetic.BinaryOperations;
import org.mips.Registers.Register;
import org.mips.Registers.RegisterFile;

import com.google.common.base.Splitter;

public final class IType extends Instruction {
    private static final int OPERAND_PART_COUNT = 3;
    private static final int REGISTER_OPERAND_COUNT = 2;
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');
    private long immediate;

    IType(int insID, String insLiteral, String opcode, String operands) throws Exception {
        this.insID = insID;
        this.insLiteral = insLiteral;
        this.opcode = opcode;
        List<String> opStrs = COMMA_SPLITTER.splitToList(Objects.requireNonNull(operands));
        if (opStrs.size() != OPERAND_PART_COUNT) {
            throw new Exception("wrong instruction format. expecting opcode register,register,immediate");
        }
        this.operandRegisterIDs = new int[REGISTER_OPERAND_COUNT];
        operandRegisterIDs[0] = Integer.parseInt(opStrs.get(0).substring(1).strip());
        operandRegisterIDs[1] = Integer.parseInt(opStrs.get(1).substring(1).strip());
        this.immediate = Long.parseLong(opStrs.get(2).strip());
    }

    @Override
    public void execute(RegisterFile rf) throws Exception {
        for (int i : this.operandRegisterIDs) {
            if (i >= rf.size()) {
                throw new Exception("invalid register number");
            }
        }
        Register rd = rf.getRegister(operandRegisterIDs[0]);
        Register rs = rf.getRegister(operandRegisterIDs[1]);
        boolean[] result;
        switch (this.opcode) {
            case "addi":
                result = BinaryOperations.AddI(rs.getRegisterArray(), this.immediate);
                break;
            case "subi":
                result = BinaryOperations.SubI(rs.getRegisterArray(), this.immediate);
                break;
            case "muli":
                result = BinaryOperations.MulI(rs.getRegisterArray(), this.immediate);
                break;
            case "divi":
                result = BinaryOperations.DivI(rs.getRegisterArray(), this.immediate);
                break;
            default:
                throw new Exception("unsupported opcode");
        }
        rd.setRegisterArray(result);
    }

    @Override
    public int[] getOperandRegisters() {
        return this.operandRegisterIDs.clone();
    }

    @Override
    public int getCycles() {
        switch (this.opcode) {
            case "muli":
                return 4;
            case "divi":
                return 4;
            default:
                return 1;
        }
    }

    @Override
    public String toString() {
        int rd = operandRegisterIDs[0];
        int rs = operandRegisterIDs[1];
        String result;

        switch (this.opcode) {
            case "addi":
                result = String.format("r%d <- r%d + %d", rd, rs, this.immediate);
                break;
            case "subi":
                result = String.format("r%d <- r%d - %d", rd, rs, this.immediate);
                break;
            case "muli":
                result = String.format("r%d <- r%d * %d", rd, rs, this.immediate);
                break;
            case "divi":
                result = String.format("r%d <- r%d / %d", rd, rs, this.immediate);
                break;
            default:
                result = "unsupported opcode";
        }

        return result;
    }
}
