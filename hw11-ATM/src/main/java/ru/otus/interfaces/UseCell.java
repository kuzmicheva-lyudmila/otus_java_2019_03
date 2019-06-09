package ru.otus.interfaces;

import ru.otus.NominalOfBanknote;

public interface UseCell {
    public void putBanknotes(int count); // положить банкноты в ячейку
    public void getBanknotes(int count); // выдать банкноты из ячейки
    public int getCountBanknotes(); // получить количество банкнот в ячейке
}
