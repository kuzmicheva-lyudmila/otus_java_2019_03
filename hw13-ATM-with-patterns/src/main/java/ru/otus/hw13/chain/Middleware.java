package ru.otus.hw13.chain;

public abstract class Middleware {
    private Middleware next;

    public Middleware linkWith(Middleware next) {
        this.next = next;
        return this.next;
    }

    public abstract Integer getBalance();

    protected Integer getBalanceNext() {
        if (this.next == null) {
            return 0;
        }
        return next.getBalance();
    }
}
