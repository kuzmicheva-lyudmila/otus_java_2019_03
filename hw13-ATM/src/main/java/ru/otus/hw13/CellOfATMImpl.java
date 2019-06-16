package ru.otus.hw13;

public class CellOfATMImpl implements CellOfATM{

    private Banknote banknote; // Банкнота, которая хранится в ячейке
    private int count;  // Количество в ячейкк

    public CellOfATMImpl(Banknote banknote) {
        this.banknote = banknote;
        this.count = 0;
    }

    public void putBanknotes(int count) {
        this.count = this.count + count;
    }

    public void getBanknotes(int count) {
        this.count = this.count - count;
    }

    public int getCountBanknotes() { // количество купюр в ячейке
        return this.count;
    }

}
