package org.mips.benchmarks;

import org.mips.Arithmetic.BinaryOperations;
import org.mips.Arithmetic.Utils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class BinaryOperationsBenchmark {
    private boolean[] addA;
    private boolean[] addB;
    private boolean[] mulA;
    private boolean[] mulB;
    private boolean[] divA;
    private boolean[] divB;

    @Setup
    public void setup() {
        addA = Utils.decimalToBooleanArray(12345, 32);
        addB = Utils.decimalToBooleanArray(6789, 32);

        mulA = Utils.decimalToBooleanArray(345, 32);
        mulB = Utils.decimalToBooleanArray(12, 32);

        divA = Utils.decimalToBooleanArray(1_234_567, 32);
        divB = Utils.decimalToBooleanArray(123, 32);
    }

    @Benchmark
    public boolean[] add32() throws Exception {
        return BinaryOperations.Add(addA, addB);
    }

    @Benchmark
    public boolean[] multiply32() throws Exception {
        return BinaryOperations.Multiply(mulA, mulB);
    }

    @Benchmark
    public boolean[] divide32() throws Exception {
        return BinaryOperations.Divide(divA, divB);
    }
}
