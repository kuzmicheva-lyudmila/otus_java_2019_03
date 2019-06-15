package ru.otus.hw11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ATMTest {
    ATM atm;

    @BeforeEach
    public void beforeATMTest() {
        atm = new ATMImpl();
        List<Banknote> userCashPut = new ArrayList<>();
        userCashPut.add(Banknote.TWO_HUNDRED);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_THOUSAND);
        userCashPut.add(Banknote.ONE_HUNDRED);
        userCashPut.add(Banknote.FIVE_HUNDRED);
        // положим в банкомат 5900 руб.
        atm.putUserCash(userCashPut);
    }

    @Test
    public void checkGetUserTest1() {
        Collection userCashGet = (ArrayList) atm.getUserCash(900);
        assertEquals(5000, this.atm.getCashBalance());
        System.out.println(userCashGet);
    }

    @Test
    public void checkGetUserTest2() {
        Collection userCashGet = (ArrayList) atm.getUserCash(0);
        assertEquals(5900, this.atm.getCashBalance());
        System.out.println(userCashGet);
    }

    @Test
    public void checkGetUserTest3() {
        Collection userCashGet = (ArrayList) atm.getUserCash(2100);
        assertEquals(5900, this.atm.getCashBalance());
        System.out.println(userCashGet);
    }
}
