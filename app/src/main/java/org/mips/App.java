package org.mips;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.mips.Instructions.Instruction;
import org.mips.Registers.Register;
import org.mips.Registers.RegisterFile;

import com.google.common.base.Splitter;

public class App {
    private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
    private static final Splitter SPACE_SPLITTER = Splitter.on(' ').limit(2);
    private static final int INS_PARTS = 2;
    private static final int COLUMN_PADDING = 2;
    private static final int PC_INDEX_WIDTH = 3;
    private static final int DECODED_COLUMN_WIDTH = 26;
    private static final int ENCODED_COLUMN_WIDTH = 32;
    private static final int CYCLES_COLUMN_WIDTH = 12;
    private static final String PC_HEADER = "PC";
    private static final String DECODED_HEADER = "Decoded";
    private static final String ENCODED_HEADER = "Encoded instructions (32-bit)";
    private static final String CYCLES_HEADER = "Clock cycles";

    private static final int MIN_ARG_COUNT = 1;
    private static final int MAX_ARG_COUNT = 2;
    private static final int DEFAULT_REGISTER_SIZE = 32;
    private static final int REGISTER_COUNT = 32;
    private static final int FILE_ARG_INDEX = 0;
    private static final int REGISTER_SIZE_ARG_INDEX = 1;
    private static final int REGISTER_SIZE_32 = 32;
    private static final int REGISTER_SIZE_64 = 64;

    public static void main(String[] args) throws Exception {
        if (args.length < MIN_ARG_COUNT || args.length > MAX_ARG_COUNT) {
            throw new IllegalArgumentException("Usage: mips-sim <file.asm> [register-size: 32|64]");
        }

        int registerSize = DEFAULT_REGISTER_SIZE;
        if (args.length == MAX_ARG_COUNT) {
            registerSize = Integer.parseInt(args[REGISTER_SIZE_ARG_INDEX]);
            if (registerSize != REGISTER_SIZE_32 && registerSize != REGISTER_SIZE_64) {
                throw new IllegalArgumentException("register-size must be 32 or 64");
            }
        }

        StringBuilder sb = new StringBuilder();
        RegisterFile rf = new RegisterFile(REGISTER_COUNT, registerSize);
        String assemblyText = Files.readString(Path.of(args[FILE_ARG_INDEX]), StandardCharsets.UTF_8);
        List<String> lines = NEWLINE_SPLITTER.splitToList(Objects.requireNonNull(assemblyText));
        long totalCycles = 0;

        List<Instruction> instructions = new ArrayList<>();

        for (String line : lines) {
            List<String> lineParts = SPACE_SPLITTER.splitToList(Objects.requireNonNull(line));
            if (lineParts.size() != INS_PARTS) {
                throw new Exception("malformed input, expected '<line-number> <opcode> <operands>'");
            }
            int id = Integer.parseInt(lineParts.get(0));
            instructions.add(Instruction.of(id, lineParts.get(1)));
        }

        String rowFormat = "PC[%" + PC_INDEX_WIDTH + "d] -> %-" + (DECODED_COLUMN_WIDTH + COLUMN_PADDING) + "s%-"
                + (ENCODED_COLUMN_WIDTH + COLUMN_PADDING) + "s%" + CYCLES_COLUMN_WIDTH + "d%n";
        String headerFormat = "%-" + (PC_INDEX_WIDTH + 7 + COLUMN_PADDING) + "s%-"
                + (DECODED_COLUMN_WIDTH + COLUMN_PADDING) + "s%-" + (ENCODED_COLUMN_WIDTH + COLUMN_PADDING)
                + "s%" + CYCLES_COLUMN_WIDTH + "s%n";

        System.out.printf(headerFormat,
                PC_HEADER,
                DECODED_HEADER,
                ENCODED_HEADER,
                CYCLES_HEADER);

        for (Instruction i : instructions) {
            sb.append(String.format(rowFormat,
                    i.getInstructionId() - 1,
                    i.getInstructionLiteral() + " :",
                    i.getBitEncodedForm(),
                    i.getCycles()));
            totalCycles += i.getCycles();
            i.execute(rf);
        }

        System.out.print(sb);
        System.out.println();
        System.out.println("After execution, contents of the register are ... ");
        for (Instruction i : instructions) {
            Register r = i.getAffectedRegister();
            System.out.printf("r%-2d -> %s%n", r.getRegisterID(), r);
        }
        System.out.println();
        System.out.println("CPI of the program: ");
        System.out.printf("CPI : %.2f%n", (double) totalCycles / instructions.size());
    }

}
