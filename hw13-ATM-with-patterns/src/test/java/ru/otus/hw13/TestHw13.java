package ru.otus.hw13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw13.command.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHw13 {
    Department department;

    private static Integer patternCommand(ATM atm, Collection<Object> userCashPut, Integer getUserCash) {
        // загрузим в ATM банкноты
        Command putUserCash = new PutUserCash(atm, userCashPut);
        putUserCash.execute();

        // извлечем деньги из ATM
        Command getCash = new GetUserCash(atm, getUserCash);
        getCash.execute();

        return (Integer) new GetCashBalance(atm).execute();
    }

    @BeforeEach
    void beforeTest() {
        department = new DepartmentImpl();

        // добавление ATM
        department.addATM(1, new ATMImpl(1));

        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(Banknote.ONE_HUNDRED);
        banknotes.add(Banknote.TWO_HUNDRED);
        department.addATM(2, new ATMImpl(2, banknotes));
    }

    @Test
    public void patternCommandTest1() {
        System.out.println(">>> PATTERN COMMAND: ATM ID = 1");

        Collection<Object> userCashPut = new ArrayList<>();
        userCashPut.add(Banknote.TWO_HUNDRED);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_THOUSAND);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_HUNDRED);

        assertEquals((Integer) 5100, patternCommand(department.getATM(1), userCashPut, 800));
    }

    @Test
    public void patternCommandTest2() {
        System.out.println(">>> PATTERN COMMAND: ATM ID = 2");

        Collection<Object> userCashPut = new ArrayList<>();
        userCashPut.add(Banknote.TWO_HUNDRED);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_THOUSAND);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_HUNDRED);

        assertEquals((Integer) 400, patternCommand(department.getATM(2), userCashPut, 800));
    }

    @Test
    public void patternChainTest(){
        Integer balance = (Integer) new GetCashBalance(department.getATM(1)).execute() + (Integer) new GetCashBalance(department.getATM(2)).execute();
        assertEquals(balance, (Integer) new GetCashBalanceAllATM(department).execute());
    }

    @Test
    public void patternMementoTest(){
        Collection<Object> userCashPut = new ArrayList<>();
        userCashPut.add(Banknote.TWO_HUNDRED);
        new PutUserCash(department.getATM(1), userCashPut).execute();

        new RestoreStateAllATM(department).execute();
        Integer balance = (Integer) new GetCashBalance(department.getATM(1)).execute() + (Integer) new GetCashBalance(department.getATM(2)).execute();
        assertEquals((Integer) 0, balance);
    }
}
