As required by course CSX3007 Computer Architecture from Assumption University School of Science and Engineering.

## Running The Simulator

From the project root, run:

```bash
./gradlew :app:run --args="<file.asm> [register-size]"
```

- `file.asm` is required.
- `register-size` is optional and accepts only `32` or `64`.
- If `register-size` is omitted, the simulator uses `32`.

Examples:

```bash
./gradlew :app:run --args="test_asm.txt"
./gradlew :app:run --args="test_asm.txt 32"
./gradlew :app:run --args="test_asm.txt 64"
```

## Clean Workspace

Generated outputs are already ignored by git (`build`, `.gradle`, `.kotlin`).

To clean local build artifacts:

```bash
./gradlew clean
```

To also clear Gradle's local project cache in this repo:

```bash
rm -rf .gradle
```

## Build Modes

Default dev checks (fast):

```bash
./gradlew :app:check
```

Release-style checks (enables ErrorProne, PMD, SpotBugs):

```bash
./gradlew :app:check -Prelease=true
```

You can also run a normal build with release analysis enabled:

```bash
./gradlew :app:build -Prelease=true
```

## Class Diagram

```mermaid
classDiagram
    class Instruction {
        <<abstract>>
        #EncodedInstruction ei
        #Register affectedRegister
        #int insID
        #String insLiteral
        #String opcode
        #int[] operandRegisterIDs
        +of(int insID, String insLiteral)$ Instruction
        +getInstructionId() int
        +getInstructionLiteral() String
        +getOperandRegisters()* int[]
        +getCycles()* int
        +execute(RegisterFile rf)* void
        +getBitEncodedForm()* String
        +getAffectedRegister()* Register
    }

    class RType {
        <<final>>
        +getBitEncodedForm() String
        +getAffectedRegister() Register
        +execute(RegisterFile rf) void
        +getOperandRegisters() int[]
        +getCycles() int
        +toString() String
    }

    class IType {
        <<final>>
        -long immediate
        +getBitEncodedForm() String
        +getAffectedRegister() Register
        +execute(RegisterFile rf) void
        +getOperandRegisters() int[]
        +getCycles() int
        +toString() String
    }

    class EncodedInstruction {
        -String opcodeBits
        -String operandBits
        +EncodedInstruction(String opcodeBits, String operandBits)
        +RTypeEncoding(String opcode, long rdID, long rsID, long rtID)$ EncodedInstruction
        +ITypeEncoding(String opcode, long rdID, long rsID, long immediate)$ EncodedInstruction
        +toString() String
    }

    class RegisterFile {
        -HashMap~Integer, Register~ registers
        -int registerCount
        +RegisterFile(int totalRegisters, int registerLength)
        +getRegister(int id) Register
        +setRegister(int id, Register register) void
        +size() int
    }

    class Register {
        -int registerID
        -boolean[] registerArray
        -long registerValue
        +getRegisterID() int
        +setRegisterArray(boolean[] arr) void
        +getRegisterArray() boolean[]
        +getDecimal() long
        +clone() Register
        +toString() String
    }

    class BinaryOperations {
        <<static>>
        +Add(boolean[] a, boolean[] b)$ boolean[]
        +Subtract(boolean[] a, boolean[] b)$ boolean[]
        +Multiply(boolean[] a, boolean[] b)$ boolean[]
        +Divide(boolean[] a, boolean[] b)$ boolean[]
        +AddI(boolean[] a, long imm)$ boolean[]
        +SubI(boolean[] a, long imm)$ boolean[]
        +MulI(boolean[] a, long imm)$ boolean[]
        +DivI(boolean[] a, long imm)$ boolean[]
    }

    class App {
        +main(String[] args)$ void
    }

    class Utils {
        <<static>>
        +bitArrayToTwosComplement(boolean[] registerArray)$ long
        +powerN(long number, int power)$ long
        +decimalToBooleanArray(long decimal, int size)$ boolean[]
        +bitStringFromDecimalWithSize(long decimal, int size)$ String
    }

    Instruction <|-- RType
    Instruction <|-- IType
    App ..> Instruction : creates via of()
    App ..> Register : prints affected registers
    App ..> RegisterFile : uses
    RType ..> RegisterFile : uses
    IType ..> RegisterFile : uses
    Instruction --> EncodedInstruction : stores
    RegisterFile "1" *-- "32" Register : contains
    RType ..> BinaryOperations : uses
    IType ..> BinaryOperations : uses
    Register ..> Utils : uses
    BinaryOperations ..> Utils : uses
    EncodedInstruction ..> Utils : uses
```

## Benchmarks (JMH)

Benchmark sources are under `app/src/jmh/java/org/mips/benchmarks/`.

- `BinaryOperationsBenchmark`
  : measures `Add`, `Multiply`, `Divide`.
- `InstructionExecutionBenchmark`
  : measures `Instruction.execute(...)` for high-level instruction paths and parser cost via `Instruction.of(...)`.

Run all benchmarks:

```bash
./gradlew :app:jmh
```

Run a single benchmark method:

```bash
./gradlew :app:jmh -Pjmh='org.mips.benchmarks.BinaryOperationsBenchmark.add32'
```
