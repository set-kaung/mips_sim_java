package org.example.Registers;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.example.Arithmetic.Utils;

public class Register implements Cloneable {
    private int registerID;
    private boolean[] registerArray;
    private long registerValue;

    public Register(int id, int registerLength) {
        this.registerID = id;
        this.registerArray = new boolean[registerLength];
        this.registerValue = 0;
    }

    public int getRegisterID() {
        return this.registerID;
    }

    public void setRegisterArray(boolean[] newRegisterArray) {
        this.registerArray = newRegisterArray.clone();
        this.registerValue = Utils.bitArrayToTwosComplement(this.registerArray);
    }

    public boolean[] getRegisterArray() {
        return this.registerArray.clone();
    }

    public long getDecimal() {
        return this.registerValue;
    }

    @Override
    public String toString() {
        return "[" + IntStream.range(0, this.registerArray.length)
                .mapToObj(i -> this.registerArray[i] ? "1" : "0")
                .collect(Collectors.joining(" ")) + "]";
    }

    @Override
    public Register clone() {
        try {
            Register newRegister = (Register) super.clone();
            newRegister.registerArray = this.registerArray.clone();
            return newRegister;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // cannot happen: Register implements Cloneable
        }
    }

}
