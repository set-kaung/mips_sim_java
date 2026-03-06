package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.example.Instructions.Instruction;
import org.example.Registers.Register;
import org.example.Registers.RegisterFile;

import com.google.common.base.Splitter;

public class App {
    private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
    private static final Splitter SPACE_SPLITTER = Splitter.on(' ').limit(2);
    private static final int INS_PARTS = 2;

    public static void main(String[] args) throws Exception {
        RegisterFile rf = new RegisterFile(32, 32);
        String assemblyText;

        try (InputStream inputStream = App.class.getResourceAsStream("/test_asm.txt")) {
            if (inputStream == null) {
                throw new IOException("Resource not found: /test_asm.txt");
            }
            assemblyText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        List<String> lines = NEWLINE_SPLITTER.splitToList(Objects.requireNonNull(assemblyText));
        for (String line : lines) {
            List<String> lineParts = SPACE_SPLITTER.splitToList(Objects.requireNonNull(line));
            if (lineParts.size() != INS_PARTS) {
                throw new Exception("malformed input, expected '<line-number> <opcode> <operands>'");
            }
            int id = Integer.parseInt(lineParts.get(0));
            Instruction ins = Instruction.of(id, lineParts.get(1));
            ins.execute(rf);
            int affectedRegisterID = ins.getOperandRegisters()[0];
            Register affectedRegister = rf.getRegister(affectedRegisterID);
            System.out.printf("%d %s%n", id, ins.getInstructionLiteral());
            System.out.println(ins);
            System.out.printf("value of affected register: r%d = %d %s%n%n", affectedRegisterID,
                    affectedRegister.getDecimal(), affectedRegister);
        }

    }
}
