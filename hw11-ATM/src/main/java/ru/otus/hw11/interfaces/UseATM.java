package ru.otus.hw11.interfaces;

import java.util.List;

public interface UseATM {
    public void putUserCash(int[] userCashes); // положить деньги в банкомат
    public List<Integer> getUserCash(int amount); // получить деньги из банкомата
    public int getCashBalance(); // баланс банкомата
}
