package org.mips.Instructions;

import java.util.Arrays;

import org.mips.Arithmetic.Utils;

public class EncodedInstruction {
    private String opcodeBits;
    private String operandBits;

    static final String[] supportedInstructions = { "mov", "add", "sub", "mul", "div", "addi", "subi", "muli", "divi" };

    public EncodedInstruction(String opcodeBits, String operandBits) {
        this.opcodeBits = opcodeBits;
        this.operandBits = operandBits;
    }

    public static EncodedInstruction RTypeEncoding(String opcode, long rdID, long rsID, long rtID) throws Exception {
        int opCodeID = Arrays.asList(supportedInstructions).indexOf(opcode);
        String opCodeBits = Utils.bitStringFromDecimalWithSize(opCodeID, 6);
        String rdBits = Utils.bitStringFromDecimalWithSize(rdID, 5);
        String rsBits = Utils.bitStringFromDecimalWithSize(rsID, 5);
        String rtBits = Utils.bitStringFromDecimalWithSize(rtID, 5);
        EncodedInstruction ei = new EncodedInstruction(opCodeBits,
                rdBits + rsBits + rtBits + "0".repeat(11));

        return ei;
    }

    public static EncodedInstruction ITypeEncoding(String opcode, long rdID, long rsID, long immediate)
            throws Exception {
        int opCodeID = Arrays.asList(supportedInstructions).indexOf(opcode);

        String opCodeBits = Utils.bitStringFromDecimalWithSize(opCodeID, 6);
        String rdBits = Utils.bitStringFromDecimalWithSize(rdID, 5);
        String rsBits = Utils.bitStringFromDecimalWithSize(rsID, 5);
        String immediateBits = Utils.bitStringFromDecimalWithSize(immediate, 16);

        EncodedInstruction ei = new EncodedInstruction(opCodeBits, rdBits + rsBits + immediateBits);

        return ei;
    }

    public String toString() {
        return this.opcodeBits + " " + this.operandBits;
    }
}
