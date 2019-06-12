package ru.otus.hw11;

import ru.otus.hw11.ATM;

import java.util.ArrayList;

public class MainClass {
    public static void main(String[] args) {
        ATM atm = new ATM();

        atm.putUserCash(new int[]{300, 100, 200, 100});
        System.out.println("cash balance: " + atm.getCashBalance());
        atm.printAboutCells();

        ArrayList userCash = (ArrayList) atm.getUserCash(500);
        System.out.println(userCash);
        System.out.println("cash balance: " + atm.getCashBalance());
        atm.printAboutCells();

    }
}
