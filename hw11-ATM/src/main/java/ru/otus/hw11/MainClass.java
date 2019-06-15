package ru.otus.hw11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        ATM atm = new ATMImpl();

        System.out.println("<<<PUT>>>");
        List<Banknote> userCashPut = new ArrayList<>();
        userCashPut.add(Banknote.TWO_HUNDRED);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_THOUSAND);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_HUNDRED);
        atm.putUserCash(userCashPut);
        System.out.println("cash balance: " + atm.getCashBalance());
        atm.printAboutCells();

        System.out.println("<<<GET>>>");
        Collection userCashGet = (ArrayList) atm.getUserCash(800);
        System.out.println(userCashGet);
        System.out.println("cash balance: " + atm.getCashBalance());
        atm.printAboutCells();
    }
}
