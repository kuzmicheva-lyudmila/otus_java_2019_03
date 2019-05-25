package ru.otus.hw8;

import ru.otus.hw8.annotations.Log;
import ru.otus.hw8.loggins.Loggins1;

public class TestLogging1 implements Loggins1 {
    @Log
    public void calculation(int param) {
        System.out.println("invoke TestLogging1.calculation");
    }

    public void calculation_without_log(int param1, String param2) {
        System.out.println("invoke TestLogging1.calculation_without_log");
    }

    @Log
    public void calculation2(int param1, String param2) {
        System.out.println("invoke TestLogging1.calculation2");
    }

    @Log
    public void calculation2(int param1) {
        System.out.println("invoke TestLogging1.calculation2");
    }
}
