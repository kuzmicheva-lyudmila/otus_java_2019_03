package ru.otus.hw13.memento;

import ru.otus.hw13.ATM;

public class Memento {
    private final ATM atm;

    public Memento(ATM atm) {
        this.atm = atm.clone();
    }

    public ATM getMemento() {
        return atm;
    }
}
