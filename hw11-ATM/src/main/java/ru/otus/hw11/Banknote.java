package ru.otus.hw11;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public enum Banknote {
    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    ONE_HUNDRED(100);

    private final int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {return  nominal;}

    public static void main(String[] args) {
        for (Banknote b : Banknote.values()) {
            System.out.println(b + " " + b.getNominal());
        }

        SortedMap<Banknote, Integer> cells = new TreeMap<>();
        cells.put(Banknote.TWO_HUNDRED, 5);
        cells.put(Banknote.ONE_HUNDRED, 3);
        cells.put(Banknote.ONE_THOUSAND, 1);

        for (Map.Entry<Banknote, Integer> item : cells.entrySet()) {
            System.out.printf("Key: %s  Value: %s \n", item.getKey().toString(), item.getValue().toString());
        }

    }
}
