package ru.otus.hw13.command;

import ru.otus.hw13.ATM;

public class SaveStateATM implements Command {
    private final ATM atm;

    public SaveStateATM(ATM atm) {
        this.atm = atm;
    }

    @Override
    public Object execute() {
        return this.atm.saveState();
    }
}
