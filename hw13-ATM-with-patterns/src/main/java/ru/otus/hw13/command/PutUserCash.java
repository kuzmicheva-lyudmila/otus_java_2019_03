package ru.otus.hw13.command;

import ru.otus.hw13.ATM;

import java.util.Collection;

public class PutUserCash implements Command {
    private final ATM atm;
    private final Collection<?> userCashes;

    public PutUserCash(ATM atm, Collection<?> userCashes) {
        this.atm = atm;
        this.userCashes = userCashes;
    }

    @Override
    public Object execute() {
        this.atm.putUserCash(userCashes);
        return null;
    }
}
