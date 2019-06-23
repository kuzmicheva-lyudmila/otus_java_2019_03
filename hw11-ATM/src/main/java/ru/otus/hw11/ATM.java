package ru.otus.hw11;

import java.util.Collection;
import java.util.List;

public interface ATM {
    void putUserCash(List<Banknote> userCashes); // положить деньги в банкомат
    Collection<Banknote> getUserCash(int amount); // получить деньги из банкомата
    int getCashBalance(); // баланс банкомата
    void printAboutCells(); // печать ячеек АТМ
}
