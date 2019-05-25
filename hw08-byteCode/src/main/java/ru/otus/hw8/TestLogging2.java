package ru.otus.hw8;

import ru.otus.hw8.annotations.Log;
import ru.otus.hw8.loggins.Loggins2;

public class TestLogging2 implements Loggins2 {
    @Log
    public void calculation() {
        System.out.println("invoke TestLogging2.calculation");
    }
}
