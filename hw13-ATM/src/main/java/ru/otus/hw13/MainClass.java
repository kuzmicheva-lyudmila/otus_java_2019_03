package ru.otus.hw13;

import ru.otus.hw13.command.Command;
import ru.otus.hw13.command.GetCashBalance;
import ru.otus.hw13.command.GetUserCash;
import ru.otus.hw13.command.PutUserCash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        Department department = new DepartmentImpl();

        // добавление ATM
        department.addATM(1, new ATMImpl(1));

        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(Banknote.ONE_HUNDRED);
        banknotes.add(Banknote.TWO_HUNDRED);
        department.addATM(2, new ATMImpl(2, banknotes));

        // выполнение команд
        System.out.println(">>> PATTERN COMMAND: ATM ID = 1");
        patternCommand(department.getATM(1));
        System.out.println(">>> PATTERN COMMAND: ATM ID = 2");
        patternCommand(department.getATM(2));

        // сумма остатков со всех ATM
        System.out.println(">>> PATTERN CHAIN");
        patternChain(department);

        // восстановление ATM до начального состояния
        System.out.println(">>>PATTERN MEMENTO");
        patternMemento(department);
    }

    // паттерн команда
    public static void patternCommand(ATM atm){
        System.out.println("PUT: ");
        Collection<Object> userCashPut = new ArrayList<>();
        userCashPut.add(Banknote.TWO_HUNDRED);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_THOUSAND);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_HUNDRED);
        Command putUserCash = new PutUserCash(atm, userCashPut);
        putUserCash.execute();

        Command getCashBalance = new GetCashBalance(atm);
        System.out.println("cash balance: " + getCashBalance.execute().toString());

        System.out.println("GET: ");
        Command getUserCash = new GetUserCash(atm, 800);
        Collection<Object> userCashGet = (ArrayList<Object>) getUserCash.execute();
        System.out.println(userCashGet);
        System.out.println("cash balance: " + getCashBalance.execute().toString());
    }

    // паттерн Цепочка обязанностей
    public static void patternChain(Department department) {
        Integer balance = department.getCashBalanceAllATM();
        System.out.println("Balance of all ATM: " + balance);
    }

    // паттерн Снимок
    public static void patternMemento(Department department) {
        department.RestoreState();
        System.out.println("ID = 1: " + department.getATM(1).getCashBalance());
        System.out.println("ID = 2: " + department.getATM(2).getCashBalance());
    }
}
