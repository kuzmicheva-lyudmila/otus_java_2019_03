package ru.otus.hw8;

import ru.otus.hw8.annotations.Log;
import ru.otus.hw8.loggins.FirstLogging;

public class TestFirstLogging implements FirstLogging {
    @Log
    public void calculation(int param) {
        System.out.println("invoke TestLogging1.calculation");
    }

    public void calculationWithoutLog(int param1, String param2) {
        System.out.println("invoke TestLogging1.calculation_without_log");
    }

    @Log
    public void calculationWithDiffParams(int param1, String param2) {
        System.out.println("invoke TestLogging1.calculation2");
    }

    @Log
    public void calculationWithDiffParams(int param1) {
        System.out.println("invoke TestLogging1.calculation2");
    }
}
