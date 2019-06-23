package ru.otus.hw11;

import java.util.*;

public class ATMImpl implements ATM{
    private SortedMap<Banknote, CellOfATM> cells;
    private int cashBalance; // баланс банкомата

    public ATMImpl() {
        this.cashBalance = 0;
        cells = new TreeMap<>();
        for (Banknote b : Banknote.values()) {
            cells.put(b, new CellOfATMImpl(b));
        }
    }

    @Override
    public void putUserCash(List<Banknote> userCashes) {
        for (int i = 0; i < userCashes.size(); i++) {
            Banknote b = userCashes.get(i);
            // добавим банкноту в ячейку
            cells.get(b).putBanknotes(1);
            // увеличим баланс
            this.cashBalance = this.cashBalance + b.getNominal();
        }
    }

    @Override
    public Collection<Banknote> getUserCash(int amount) {
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
    public int getCashBalance() {
        return this.cashBalance;
    }

    @Override
    public void printAboutCells() {
        for (Map.Entry<Banknote, CellOfATM> item : this.cells.entrySet()) {
            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue().getCountBanknotes());
        }
    }
}
