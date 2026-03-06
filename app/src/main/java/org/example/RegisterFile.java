package org.example;

import java.util.HashMap;

public class RegisterFile {
    private HashMap<Integer, Register> registers;

    public RegisterFile(int totalRegisters, int registerLength) {
        this.registers = new HashMap<Integer, Register>();
        for (int i = 0; i < totalRegisters; i++) {
            registers.put(i, new Register(i, registerLength));
        }
    }

    public Register getRegister(int id) {
        return this.registers.get(id);
    }

    public void setRegister(int id, Register newRegister) {
        this.registers.put(id, newRegister.clone());
    }

}
