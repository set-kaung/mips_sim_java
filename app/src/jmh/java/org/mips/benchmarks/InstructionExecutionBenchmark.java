package org.mips.benchmarks;

import org.mips.Instructions.Instruction;
import org.mips.Registers.RegisterFile;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class InstructionExecutionBenchmark {
    private RegisterFile addRegisterFile;
    private RegisterFile mulRegisterFile;
    private RegisterFile movRegisterFile;

    private Instruction addInstruction;
    private Instruction mulInstruction;
    private Instruction movInstruction;

    @Setup
    public void setup() throws Exception {
        addRegisterFile = new RegisterFile(32, 32);
        mulRegisterFile = new RegisterFile(32, 32);
        movRegisterFile = new RegisterFile(32, 32);

        addInstruction = Instruction.of(1, "add r3,r1,r2");
        mulInstruction = Instruction.of(2, "mul r3,r1,r2");
        movInstruction = Instruction.of(3, "mov r3,r1");

        addRegisterFile.getRegister(1).setRegisterArray(org.mips.Arithmetic.Utils.decimalToBooleanArray(200, 32));
        addRegisterFile.getRegister(2).setRegisterArray(org.mips.Arithmetic.Utils.decimalToBooleanArray(50, 32));

        mulRegisterFile.getRegister(1).setRegisterArray(org.mips.Arithmetic.Utils.decimalToBooleanArray(17, 32));
        mulRegisterFile.getRegister(2).setRegisterArray(org.mips.Arithmetic.Utils.decimalToBooleanArray(23, 32));

        movRegisterFile.getRegister(1).setRegisterArray(org.mips.Arithmetic.Utils.decimalToBooleanArray(999, 32));
    }

    @Benchmark
    public void executeAddRType() throws Exception {
        addInstruction.execute(addRegisterFile);
    }

    @Benchmark
    public void executeMulRType() throws Exception {
        mulInstruction.execute(mulRegisterFile);
    }

    @Benchmark
    public void executeMovPseudoInstruction() throws Exception {
        movInstruction.execute(movRegisterFile);
    }

    @Benchmark
    public Instruction parseITypeInstruction() throws Exception {
        return Instruction.of(4, "addi r3,r1,2");
    }
}
