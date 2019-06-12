package ru.otus.hw11;

import ru.otus.hw11.interfaces.UseATM;

import java.util.*;

public class ATM implements UseATM {
    private SortedMap<NominalOfBanknote, CellOfATM> cells = new TreeMap<>();
    private int cashBalance; // баланс банкомата

    public ATM() {
        this.cashBalance = 0;
    }

    @Override
    public void putUserCash(int[] userCashes) {
        Set<NominalOfBanknote> listOfNominals = cells.keySet();

        for (int i = 0; i < userCashes.length; i++) {
            NominalOfBanknote banknote = new NominalOfBanknote(userCashes[i]);

            // если уже есть ячейка с необходимой банкнотой
            if (listOfNominals.contains(banknote)) {
                CellOfATM cell = cells.get(banknote);
                cell.putBanknotes(1);
            }
            // иначе добавим учейку
            else {
                cells.put(banknote, new CellOfATM(1));
            }
            this.cashBalance = this.cashBalance + banknote.getAmount();
        }
    }

    @Override
    public List<Integer> getUserCash(int amount) {
        List<Integer> userCash = new ArrayList<>();

        if (amount <= this.cashBalance) {
            int tempAmount = amount;

            for(Map.Entry<NominalOfBanknote, CellOfATM> item : this.cells.entrySet()){
                if (tempAmount <= 0) break;

                NominalOfBanknote banknote = item.getKey();
                int nominal = banknote.getAmount();
                CellOfATM cell = item.getValue();

                int needBanknotesCnt = (int) Math.floor(tempAmount / nominal);

                int banknotesOnCellCnt = cell.getCountBanknotes();

                if (needBanknotesCnt > 0 && banknotesOnCellCnt > 0) {
                    int count = Math.min(needBanknotesCnt, banknotesOnCellCnt);
                    tempAmount = tempAmount - count * nominal;

                    for (int i = 0; i < count; i++) userCash.add(Integer.valueOf(nominal));
                }
            }

            if (tempAmount == 0) {  // нашли нужно количество банкнот
                // уменьшим кол-во банкнот в ячейке
                for (Integer i : userCash) {
                    NominalOfBanknote banknote = new NominalOfBanknote(i.intValue());

                    CellOfATM cell = cells.get(banknote);
                    cell.getBanknotes(1);
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

    public void printAboutCells() {
        for (Map.Entry<NominalOfBanknote, CellOfATM> item : this.cells.entrySet()) {
            System.out.printf("Key: %d  Value: %s \n", item.getKey().getAmount(), item.getValue().getCountBanknotes());
        }
    }
}
