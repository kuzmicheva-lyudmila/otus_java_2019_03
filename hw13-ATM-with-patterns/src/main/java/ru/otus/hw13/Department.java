package ru.otus.hw13;

public interface Department {
    void addATM(Integer ID, ATM atm);
    void removeATM(Integer idATM);
    ATM getATM(Integer ID);

    void restoreState(); // восстановить начальное состояние АТМ
    Integer getCashBalanceAllATM(); // баланс всех АТМ
}
