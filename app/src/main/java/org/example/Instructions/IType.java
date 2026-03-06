package org.example.Instructions;

import java.util.List;
import java.util.Objects;

import com.google.common.base.Splitter;

import org.example.Arithmetic.BinaryOperations;
import org.example.Registers.Register;
import org.example.Registers.RegisterFile;

public final class IType extends Instruction {
    private static final int INSTRUCTION_PART_COUNT = 2;
    private static final int OPERAND_PART_COUNT = 3;
    private static final int REGISTER_OPERAND_COUNT = 2;
    private static final Splitter SPACE_SPLITTER = Splitter.on(' ');
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');
    private long immediate;

    public IType(int insID, String insLiteral) throws Exception {
        this.insID = insID;
        this.insLiteral = insLiteral;
        List<String> parts = SPACE_SPLITTER.splitToList(Objects.requireNonNull(insLiteral));
        if (parts.size() != INSTRUCTION_PART_COUNT) {
            throw new Exception("wrong instruction format. expecting opcode register,register,immediate");
        }
        this.opcode = parts.get(0);
        List<String> opStrs = COMMA_SPLITTER.splitToList(Objects.requireNonNull(parts.get(1)));
        if (opStrs.size() != OPERAND_PART_COUNT) {
            throw new Exception("wrong instruction format. expecting opcode register,register,immediate");
        }
        this.operandRegisterIDs = new int[REGISTER_OPERAND_COUNT];
        operandRegisterIDs[0] = Integer.parseInt(opStrs.get(0).substring(1).strip());
        operandRegisterIDs[1] = Integer.parseInt(opStrs.get(1).substring(1).strip());
        this.immediate = Long.parseLong(opStrs.get(2).substring(1).strip());
    }

    @Override
    public void execute(RegisterFile rf) throws Exception {
        for (int i : this.operandRegisterIDs) {
            if (i >= rf.size()) {
                throw new Exception("invalid register number");
            }
        }
        Register rd = rf.getRegister(operandRegisterIDs[0]);
        Register rt = rf.getRegister(operandRegisterIDs[1]);
        boolean[] result;
        switch (this.opcode) {
            case "addi":
                result = BinaryOperations.AddI(rt.getRegisterArray(), this.immediate);
                break;
            case "subi":
                result = BinaryOperations.SubI(rt.getRegisterArray(), this.immediate);
                break;
            case "muli":
                result = BinaryOperations.MulI(rt.getRegisterArray(), this.immediate);
                break;
            case "divi":
                result = BinaryOperations.DivI(rt.getRegisterArray(), this.immediate);
                break;
            default:
                throw new Exception("unsupported opcode");
        }
        rd.setRegisterArray(result);
    }
}
