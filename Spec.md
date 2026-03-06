# MIPS Simulator Specification

## Instruction Format

```
<program>     ::= { <line> }
<line>        ::= <line-number> <instruction>

<line-number> ::= <non-zero-digit> { <digit> }

<instruction> ::= <opcode> <register> "," <register> "," <register>

<opcode>      ::= "add" | "sub" | "mul" | "div"

<register>    ::= "r" <register-number>
<register-number> ::= "0"
                    | <non-zero-digit> { <digit> }   (* 1–9 *)
                    | "1" <digit>                    (* 10–19 *)
                    | "2" <digit>                    (* 20–29 *)
                    | "3" ( "0" | "1" )              (* 30–31 *)

<non-zero-digit> ::= "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
<digit>          ::= "0" | <non-zero-digit>
```

### Instruction Semantics

| Instruction      | Operation    | Notes                                                            |
| ---------------- | ------------ | ---------------------------------------------------------------- |
| `add rd, rs, rt` | rd = rs + rt | Raises exception on overflow/underflow                           |
| `sub rd, rs, rt` | rd = rs - rt | Implemented as rs + (−rt)                                        |
| `mul rd, rs, rt` | rd = rs × rt | Sign-magnitude multiplication                                    |
| `div rd, rs, rt` | rd = rs ÷ rt | Integer (truncated) division; raises exception on divide-by-zero |

### Operand Conventions

- **rd** — destination register (result is written here)
- **rs** — first source register
- **rt** — second source register
- Registers are named `r0` through `r31`
- All values are stored as two's complement signed integers

### Error Conditions

- `add`/`sub`: throws on signed overflow or underflow
- `div`: throws on division by zero
- All operations: throws if the two source registers differ in bit-width
