package ru.otus.hw8;

import ru.otus.hw8.loggins.Loggins1;
import ru.otus.hw8.loggins.Loggins2;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Loggins1 log1 = (Loggins1) IoC.createTestClass(TestLogging1.class);
        log1.calculation(1);
        log1.calculation_without_log(1, "Test");
        log1.calculation2(1, "Test");
        log1.calculation2(1);

        Loggins2 log2 = (Loggins2) IoC.createTestClass(TestLogging2.class);
        log2.calculation();
    }

}
