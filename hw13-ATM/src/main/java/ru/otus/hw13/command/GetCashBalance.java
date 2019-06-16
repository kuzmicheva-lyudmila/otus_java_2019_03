package ru.otus.hw13.command;

import ru.otus.hw13.ATM;
import ru.otus.hw13.chain.Middleware;

import java.util.Collection;

public class GetCashBalance implements Command{
    private final ATM atm;

    public GetCashBalance(ATM atm) {
        this.atm = atm;
    }

    @Override
    public Object execute() {
        return this.atm.getCashBalance();
    }

}
