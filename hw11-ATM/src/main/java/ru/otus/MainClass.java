package ru.otus;

import java.util.ArrayList;
import java.util.Map;

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
