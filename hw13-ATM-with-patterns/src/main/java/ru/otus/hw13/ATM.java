package ru.otus.hw13;

import ru.otus.hw13.chain.Handler;
import ru.otus.hw13.chain.HandlerATM;
import ru.otus.hw13.memento.Memento;

import java.util.Collection;

public interface ATM {
    void putUserCash(Collection<?> userCashes); // положить деньги в банкомат
    Collection<?> getUserCash(int amount); // получить деньги из банкомата
    Object getCashBalance(); // баланс банкомата
    Integer getID(); // получить уникльный идентификтор
    void printAboutCells(); // печать ячеек АТМ
    ATM clone();

    Memento saveState(); // сохранение состояния
    void restoreState(Memento memento); // восстановление состояния
}
