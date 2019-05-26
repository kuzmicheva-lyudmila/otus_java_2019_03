package ru.otus.hw8;

import ru.otus.hw8.annotations.Log;
import ru.otus.hw8.loggins.SecondLogging;

public class TestSecondLogging implements SecondLogging {
    @Log
    public void calculation() {
        System.out.println("invoke TestLogging2.calculation");
    }
}
