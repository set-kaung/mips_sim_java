package org.mips.Instructions;

import java.util.List;
import java.util.Objects;

import org.mips.Arithmetic.BinaryOperations;
import org.mips.Registers.Register;
import org.mips.Registers.RegisterFile;

import com.google.common.base.Splitter;

public final class RType extends Instruction {
    private static final int OPERAND_PART_COUNT = 3;
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');

    RType(int insID, String insLiteral, String opcode, String operands) throws Exception {
        this.insID = insID;
        this.insLiteral = insLiteral;
        this.opcode = opcode;
        List<String> opStrs = COMMA_SPLITTER.splitToList(Objects.requireNonNull(operands));
        if (opStrs.size() != OPERAND_PART_COUNT) {
            throw new Exception("wrong instruction format. expecting opcode register,register,register");
        }
        this.operandRegisterIDs = new int[OPERAND_PART_COUNT];
        operandRegisterIDs[0] = Integer.parseInt(opStrs.get(0).substring(1).strip());
        operandRegisterIDs[1] = Integer.parseInt(opStrs.get(1).substring(1).strip());
        operandRegisterIDs[2] = Integer.parseInt(opStrs.get(2).substring(1).strip());
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
        Register rt = rf.getRegister(operandRegisterIDs[2]);
        boolean[] result;
        boolean[] A = rs.getRegisterArray();
        boolean[] B = rt.getRegisterArray();
        switch (this.opcode) {
            case "add":
                result = BinaryOperations.Add(A, B);
                break;
            case "sub":
                result = BinaryOperations.Subtract(A, B);
                break;
            case "mul":
                result = BinaryOperations.Multiply(A, B);
                break;
            case "div":
                result = BinaryOperations.Divide(A, B);
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
    public String toString() {
        int rd = operandRegisterIDs[0];
        int rs = operandRegisterIDs[1];
        int rt = operandRegisterIDs[2];
        String result;
        switch (this.opcode) {
            case "add":
                if (rt == 0) {
                    result = String.format("r%d <- r%d", rd, rs);
                } else {
                    result = String.format("r%d <- r%d + r%d", rd, rs, rt);
                }
                break;
            case "sub":
                result = String.format("r%d <- r%d - r%d", rd, rs, rt);
                break;
            case "mul":
                result = String.format("r%d <- r%d * r%d", rd, rs, rt);
                break;
            case "div":
                result = String.format("r%d <- r%d / r%d", rd, rs, rt);
                break;
            default:
                result = "unsupported opcode";
        }
        return result;
    }

}
