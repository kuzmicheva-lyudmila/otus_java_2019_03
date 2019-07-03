package ru.otus.hw13.command;

import ru.otus.hw13.ATM;
import ru.otus.hw13.memento.Memento;

public class RestoreStateATM implements Command{
    private final ATM atm;
    private final Memento memento;

    public RestoreStateATM(ATM atm, Memento memento) {
        this.atm = atm;
        this.memento = memento;
    }

    @Override
    public Object execute() {
        this.atm.restoreState(this.memento);
        return null;
    }
}
