package ru.otus;

public class NominalOfBanknote implements Comparable<NominalOfBanknote>{
    private final int amount;

    public NominalOfBanknote(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(NominalOfBanknote o) {
        return Integer.compare(o.amount, this.amount);
    }

    @Override
    public int hashCode() {
        return this.amount;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (this.getClass() != otherObject.getClass()) return false;
        NominalOfBanknote other = (NominalOfBanknote) otherObject;
        return (this.amount == other.amount);
    }
}
