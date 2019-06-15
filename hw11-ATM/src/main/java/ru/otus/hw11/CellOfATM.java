package ru.otus.hw11;

public interface CellOfATM {
    void putBanknotes(int count); // положить банкноты в ячейку
    void getBanknotes(int count); // выдать банкноты из ячейки
    int getCountBanknotes(); // получить количество банкнот в ячейке
}
