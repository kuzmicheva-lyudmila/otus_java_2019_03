package ru.otus.hw11;

public class CellOfATMImpl implements CellOfATM{

    private Banknote banknote; // Банкнота, которая хранится в ячейке
    private int count;  // Количество в ячейкк

    public CellOfATMImpl(Banknote banknote) {
        this.banknote = banknote;
        this.count = 0;
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
