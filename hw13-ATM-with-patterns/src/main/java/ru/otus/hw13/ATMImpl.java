package ru.otus.hw13;

import ru.otus.hw13.chain.Middleware;
import ru.otus.hw13.memento.Memento;

import java.util.*;

public class ATMImpl extends Middleware implements ATM{
    private final Integer ID; // уникальный идентификтор ATM
    private SortedMap<Banknote, CellOfATM> cells;
    private Integer cashBalance; // баланс банкомата

    public ATMImpl(Integer ID) {
        this.ID = ID;
        this.cashBalance = 0;
        cells = new TreeMap<>();
        for (Banknote b : Banknote.values()) {
            cells.put(b, new CellOfATMImpl(b));
        }
    }

    public ATMImpl(Integer ID, Collection<Banknote> banknotes) {
        this.ID = ID;
        this.cashBalance = 0;
        cells = new TreeMap<>();
        for (Banknote b : banknotes) {
            cells.put(b, new CellOfATMImpl(b));
        }
    }

    @Override
    public void putUserCash(Collection<?> userCashes) {
        for (Banknote b : (Collection<Banknote>) userCashes) {
            if (cells.containsKey(b)) {
                // добавим банкноту в ячейку
                cells.get(b).putBanknotes(1);
                // увеличим баланс
                this.cashBalance = this.cashBalance + b.getNominal();
            }
        }
    }

    @Override
    public Collection<?> getUserCash(int amount) {
        Collection<Banknote> userCash = new ArrayList<>();

        if (amount <= this.cashBalance) {
            int tempAmount = amount;

            for(Map.Entry<Banknote, CellOfATM> item : this.cells.entrySet()){
                if (tempAmount <= 0) { break; }

                Banknote banknote = item.getKey();
                CellOfATM cell = item.getValue();

                int needBanknotesCnt = (int)(tempAmount / banknote.getNominal());

                int banknotesOnCellCnt = cell.getCountBanknotes();

                if (needBanknotesCnt > 0 && banknotesOnCellCnt > 0) {
                    int count = Math.min(needBanknotesCnt, banknotesOnCellCnt);
                    tempAmount = tempAmount - count * banknote.getNominal();

                    for (int i = 0; i < count; i++) userCash.add(banknote);
                }
            }

            if (tempAmount == 0) {  // нашли нужно количество банкнот
                // уменьшим кол-во банкнот в ячейке и баланс банкомата
                for (Banknote b : userCash) {
                    cells.get(b).getBanknotes(1);
                }
                this.cashBalance = this.cashBalance - amount;
            }
            else {
                userCash.clear();
            }
        }

        return userCash;
    }

    @Override
    public Integer getCashBalance() {
        return this.cashBalance;
    }

    @Override
    public Integer getID() {
        return this.ID;
    }

    @Override
    public void printAboutCells() {
        for (Map.Entry<Banknote, CellOfATM> item : this.cells.entrySet()) {
            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue().getCountBanknotes());
        }
    }

    @Override
    public ATMImpl clone() {
        ATMImpl atm = new ATMImpl(this.ID, this.cells.keySet());

        for (Map.Entry<Banknote, CellOfATM> item : atm.cells.entrySet()) {
            Banknote banknote = item.getKey();
            CellOfATM cell = item.getValue();
            atm.cells.get(banknote).putBanknotes(this.cells.get(banknote).getCountBanknotes());
        }

        atm.cashBalance = this.cashBalance;

        return atm;
    }

    @Override
    public Memento saveState() {
        return new Memento(this);
    }

    @Override
    public void restoreState(Memento memento) {
        ATMImpl atm = (ATMImpl) memento.getMemento();
        this.cells = atm.cells;
        this.cashBalance = atm.cashBalance;
    }

    @Override
    public Integer getBalance() {
        return this.cashBalance + getBalanceNext();
    }
}
