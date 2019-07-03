package ru.otus.hw13.command;

import ru.otus.hw13.Department;

public class GetCashBalanceAllATM implements Command {
    private final Department department;

    public GetCashBalanceAllATM(Department department) {
        this.department = department;
    }

    @Override
    public Object execute() {
        return this.department.getCashBalanceAllATM();
    }
}
