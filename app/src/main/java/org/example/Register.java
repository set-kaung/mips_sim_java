package org.example;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Register implements Cloneable {
    private int registerID;
    private boolean[] registerArray;
    private long registerValue;

    public Register(int id, int registerLength) {
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

    public String toString() {
        return "[" + IntStream.range(0, this.registerArray.length)
                .mapToObj(i -> this.registerArray[i] ? "1" : "0")
                .collect(Collectors.joining(" ")) + "]";
    }

    @Override
    public Register clone() {
        Register newRegister = new Register(this.registerID, this.registerArray.length);
        newRegister.registerArray = this.registerArray.clone();
        newRegister.registerValue = this.registerValue;

        return newRegister;
    }

}
