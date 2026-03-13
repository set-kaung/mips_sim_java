package org.mips;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    @SuppressWarnings("PMD.CloseResource") // `originalOut` references global System.out and must not be closed.
    void mainExecutesProgramAndPrintsAverageCpi() throws Exception {
        Path program = Files.createTempFile("mips-program", ".asm");
        Files.writeString(program, String.join(System.lineSeparator(),
                "1 add r1,r1,r0",
                "2 add r2,r2,r0",
                "3 add r3,r1,r2"), StandardCharsets.UTF_8);

        PrintStream originalOut = System.out;

        String printed;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
                PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            App.main(new String[] { program.toString() });
            printed = output.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(originalOut);
            Files.deleteIfExists(program);
        }

        assertNotNull(printed);
        assertFalse(printed.isBlank());

    }

    @Test
    @SuppressWarnings("PMD.CloseResource") // `originalOut` references global System.out and must not be closed.
    void mainAcceptsOptionalRegisterSize() throws Exception {
        Path program = Files.createTempFile("mips-program", ".asm");
        Files.writeString(program, String.join(System.lineSeparator(),
                "1 add r1,r1,r0"), StandardCharsets.UTF_8);

        PrintStream originalOut = System.out;

        String printed;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
                PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            App.main(new String[] { program.toString(), "32" });
            printed = output.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(originalOut);
            Files.deleteIfExists(program);
        }

        assertNotNull(printed);
        assertFalse(printed.isBlank());
    }

    @Test
    void mainRejectsInvalidRegisterSize() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> App.main(new String[] { "program.asm", "16" }));
        assertEquals("register-size must be 32 or 64", exception.getMessage());
    }
}
