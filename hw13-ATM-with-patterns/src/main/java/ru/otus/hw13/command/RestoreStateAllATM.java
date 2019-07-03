package ru.otus.hw13.command;

import ru.otus.hw13.Department;

public class RestoreStateAllATM implements Command {
    private final Department department;

    public RestoreStateAllATM(Department department) {
        this.department = department;
    }

    @Override
    public Object execute() {
        this.department.restoreState();
        return null;
    }
}
