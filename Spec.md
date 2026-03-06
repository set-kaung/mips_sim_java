# MIPS Simulator Specification

## Instruction Format

```
<program>     ::= { <line> }
<line>        ::= <line-number> <instruction>

<line-number> ::= <non-zero-digit> { <digit> }

<instruction> ::= <r-instruction> | <i-instruction> | <pseudo-instruction>

<r-instruction>      ::= <r-opcode> <register> "," <register> "," <register>
<i-instruction>      ::= <i-opcode> <register> "," <register> "," "#" <number>
<pseudo-instruction> ::= "mov" <register> "," <register>

<r-opcode>    ::= "add" | "sub" | "mul" | "div"
<i-opcode>    ::= "addi" | "subi" | "muli" | "divi"

<register>    ::= "r" <register-number>
<register-number> ::= "0"
                    | <non-zero-digit> { <digit> }   (* 1–9 *)
                    | "1" <digit>                    (* 10–19 *)
                    | "2" <digit>                    (* 20–29 *)
                    | "3" ( "0" | "1" )              (* 30–31 *)

<number>         ::= <non-zero-digit> { <digit> }

<non-zero-digit> ::= "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
<digit>          ::= "0" | <non-zero-digit>
```

### Instruction Semantics

| Instruction         | Operation     | Notes                                                            |
| ------------------- | ------------- | ---------------------------------------------------------------- |
| `add rd, rs, rt`    | rd = rs + rt  | Raises exception on overflow/underflow                           |
| `sub rd, rs, rt`    | rd = rs − rt  | Implemented as rs + (−rt)                                        |
| `mul rd, rs, rt`    | rd = rs × rt  | Sign-magnitude multiplication                                    |
| `div rd, rs, rt`    | rd = rs ÷ rt  | Integer (truncated) division; raises exception on divide-by-zero |
| `addi rd, rs, #imm` | rd = rs + imm | Immediate addition                                               |
| `subi rd, rs, #imm` | rd = rs − imm | Immediate subtraction                                            |
| `muli rd, rs, #imm` | rd = rs × imm | Immediate multiplication                                         |
| `divi rd, rs, #imm` | rd = rs ÷ imm | Immediate division                                               |
| `mov rd, rs`        | rd = rs       | Pseudo-instruction; assembler expands to `add rd, rs, r0`        |

### Operand Conventions

- **rd** — destination register (result is written here)
- **rs** — first source register
- **rt** — second source register
- **imm** — immediate (integer literal) value
- Registers are named `r0` through `r31`
- All values are stored as two's complement signed integers

### Error Conditions

- `add`/`sub`/`addi`/`subi`: throws on signed overflow or underflow
- `div`/`divi`: throws on division by zero
- All operations: throws if the two source registers differ in bit-width

### Caveats

- Writes to `r0` are always silently ignored. It is hardwired to zero.

---

## Opcode Encoding

| Mnemonic | Opcode  |
| -------- | ------- |
| `mov`    | `00000` |
| `add`    | `00001` |
| `sub`    | `00010` |
| `mul`    | `00011` |
| `div`    | `00100` |
| `addi`   | `00101` |
| `subi`   | `00110` |
| `muli`   | `00111` |
| `divi`   | `01000` |

---

## CPI (Clocks Per Instruction)

CPI measures how many clock cycles each instruction takes to complete. In a real pipelined processor,
CPI approaches 1 when there are no hazards. This simulator models a simple non-pipelined (single-cycle)
CPU where each instruction takes a fixed number of clock cycles.

### CPI Table

| Instruction    | CPI | Rationale                                                        |
| -------------- | --- | ---------------------------------------------------------------- |
| `add`, `sub`   | 1   | Simple ALU operation; single adder pass                          |
| `addi`, `subi` | 1   | Same path as add/sub; immediate requires no extra register fetch |
| `mul`, `muli`  | 4   | Iterative shift-and-add; proportional to register bit-width      |
| `div`, `divi`  | 4   | Iterative long division                                          |
| `mov`          | 1   | Expands to `add rd, rs, r0`; same cost as add                    |

### Calculating Overall Performance

Given a program with $N$ instructions:

$$\text{Total Cycles} = \sum_{i=1}^{N} \text{CPI}_i$$

$$\text{Execution Time} = \text{Total Cycles} \times T_{clock}$$

where $T_{clock}$ is the period of one clock cycle (e.g. 1 ns for a 1 GHz CPU).

### Implementation Notes

The simulator tracks CPI per instruction and accumulates a **total cycle count** over the program.
After all instructions execute, it prints:

- Cycles consumed by each instruction
- Total cycle count for the program
- Average CPI across the program
