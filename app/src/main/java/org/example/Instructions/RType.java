package org.example.Instructions;

import java.util.List;
import java.util.Objects;

import com.google.common.base.Splitter;

import org.example.Arithmetic.BinaryOperations;
import org.example.Registers.Register;
import org.example.Registers.RegisterFile;

public final class RType extends Instruction {
    private static final int INSTRUCTION_PART_COUNT = 2;
    private static final int OPERAND_PART_COUNT = 3;
    private static final Splitter SPACE_SPLITTER = Splitter.on(' ');
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');

    public RType(int insID, String insLiteral) throws Exception {
        this.insID = insID;
        this.insLiteral = insLiteral;
        List<String> parts = SPACE_SPLITTER.splitToList(Objects.requireNonNull(insLiteral));
        if (parts.size() != INSTRUCTION_PART_COUNT) {
            throw new Exception("wrong instruction format. expecting opcode register,register,register");
        }
        this.opcode = parts.get(0);
        List<String> opStrs = COMMA_SPLITTER.splitToList(Objects.requireNonNull(parts.get(1)));
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
}
