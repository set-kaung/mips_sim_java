package org.example.Registers;

import java.util.HashMap;

public class RegisterFile {
    private HashMap<Integer, Register> registers;
    private int registerCount;

    public RegisterFile(int totalRegisters, int registerLength) {
        this.registers = new HashMap<Integer, Register>();
        for (int i = 0; i < totalRegisters; i++) {
            registers.put(i, new Register(i, registerLength));
        }
        this.registerCount = totalRegisters;
    }

    public Register getRegister(int id) {
        return this.registers.get(id);
    }

    public int size() {
        return this.registerCount;
    }

    public void setRegister(int id, Register newRegister) {
        this.registers.put(id, newRegister.clone());
    }

}
