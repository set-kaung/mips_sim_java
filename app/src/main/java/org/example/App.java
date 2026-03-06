package org.example;

import org.example.Arithmetic.BinaryOperations;
import org.example.Registers.Register;
import org.example.Registers.RegisterFile;

public class App {

    public static void main(String[] args) {
        RegisterFile rf = new RegisterFile(5, 32);

        Register r1 = rf.getRegister(1);
        Register r2 = rf.getRegister(2);
        try {
            // set r1 to 5
            r1.setRegisterArray(BinaryOperations.AddI(r1.getRegisterArray(), 5));
            // set r2 to 2
            r2.setRegisterArray(BinaryOperations.AddI(r2.getRegisterArray(), 2));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
