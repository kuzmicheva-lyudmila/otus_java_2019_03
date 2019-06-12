package ru.otus.hw11;

import ru.otus.hw11.interfaces.UseCell;

public class CellOfATM implements UseCell {
    private int count;  // Количество в ячейкк

    public CellOfATM(int count) {
        this.count = count;
    }

    @Override
    public void putBanknotes(int count) {
        this.count = this.count + count;
    }

    @Override
    public void getBanknotes(int count) {
        this.count = this.count - count;
    }

    @Override
    public int getCountBanknotes() { // количество купюр в ячейке
        return this.count;
    }
}
