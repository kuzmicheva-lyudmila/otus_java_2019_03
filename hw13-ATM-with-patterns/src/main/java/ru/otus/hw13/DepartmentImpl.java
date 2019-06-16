package ru.otus.hw13;

import ru.otus.hw13.chain.Middleware;
import ru.otus.hw13.memento.Memento;

import java.util.*;

public class DepartmentImpl implements Department {
    private Map<Integer, ATM> atms;
    private Map<Integer, Memento> mementos; // снимки начального состояния ATM

    public DepartmentImpl() {
        atms = new HashMap<>();
        mementos = new HashMap<>();
    }

    @Override
    public void addATM(Integer ID, ATM atm) {
        this.atms.put(ID, atm);
        // сделаем снимок начального состояния ATM
        this.mementos.put(ID, atm.saveState());
    }

    @Override
    public void removeATM(Integer idATM) {
        atms.remove(idATM);
    }

    @Override
    public ATM getATM(Integer ID) {
        return atms.get(ID);
    }

    @Override
    public void RestoreState() {
        for(Map.Entry<Integer, ATM> item : this.atms.entrySet()) {
            ATM atm = item.getValue();
            atm.restoreState(mementos.get(item.getKey()));
        }
    }

    @Override
    public Integer getCashBalanceAllATM() {
        Middleware headMiddleware = null;
        Middleware middleware = null;

        // построим цепочку из ATM
        Boolean isFirst = true;
        for (ATM atm : this.atms.values()) {
            if (isFirst) {
                middleware = (ATMImpl) atm;
                headMiddleware = middleware; // заполминаем начало цепочки
                isFirst = false;
            }
            else {
                middleware = middleware.linkWith((ATMImpl) atm);
            }
        }

        // подсчитаем сумму
        Integer balance = headMiddleware.getBalance();
        return balance;
    }
}
