package ru.otus.hw13.command;

import ru.otus.hw13.ATM;

public class GetUserCash implements Command {
    private final ATM atm;
    private final int amount;

    public GetUserCash(ATM atm, int amount) {
        this.atm = atm;
        this.amount = amount;
    }

    @Override
    public Object execute() {
        return this.atm.getUserCash(this.amount);
    }
}