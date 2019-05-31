package ru.otus.hw8;

import ru.otus.hw8.loggins.FirstLogging;
import ru.otus.hw8.loggins.SecondLogging;

public class Demo {
    public static void main(String[] args) throws Exception {
        FirstLogging log1 = (FirstLogging) IoC.createTestClass(TestFirstLogging.class);
        log1.calculation(1);
        log1.calculationWithoutLog(1, "Test");
        log1.calculationWithDiffParams(1, "Test");
        log1.calculationWithDiffParams(1);

        SecondLogging log2 = (SecondLogging) IoC.createTestClass(TestSecondLogging.class);
        log2.calculation();
    }

}
