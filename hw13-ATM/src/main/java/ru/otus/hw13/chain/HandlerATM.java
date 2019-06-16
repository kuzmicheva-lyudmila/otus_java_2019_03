package ru.otus.hw13.chain;

public class HandlerATM{
    private Handler next;

    public Handler setNext(Handler h) {
        this.next = next;
        return next;
    }

    public Integer getBalance(Integer balance) {
        if (this.next == null) {
            return balance;
        }

        return balance + next.getBalance(balance);
    }
}
