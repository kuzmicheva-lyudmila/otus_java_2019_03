package ru.otus.hw;

import ru.otus.hw.annotations.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestRunner {
    public static void main(String[] args) {
        run(AnnotationsTest.class);
    }

    private static void invokeMethod(Method method, Object object) {
        try {
            method.invoke(object, null);
        } catch (Exception e) {
            System.out.println(method.getName() + " executed with error: " + e.getMessage());
        }
    }

    private static void run(Class<?> testClass) {
        Method[] declaredMethod = testClass.getDeclaredMethods();
        List<Method> arrBeforeAllMethods = new ArrayList<>();   // список методов помеченных аннотацией BeforeAll
        List<Method> arrAfterAllMethods = new ArrayList<>();   // список методов помеченных аннотацией AfterAll
        List<Method> arrBeforeMethods = new ArrayList<>();   // список методов помеченных аннотацией Before
        List<Method> arrAfterMethods = new ArrayList<>();   // список методов помеченных аннотацией After
        List<Method> arrTestMethods = new ArrayList<>();   // список методов помеченных аннотацией Test

        // заполнение массивов с методами
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeAll.class)) {
                if ((method.getModifiers() & Modifier.STATIC) != 0) {
                    arrBeforeAllMethods.add(method);
                } else {
                    Logger.getGlobal().info("Annotation BeforeAll only use on static method. Method [" + method.getName() + "] isn't static");
                }
            }

            if (method.isAnnotationPresent(AfterAll.class)) {
                if ((method.getModifiers() & Modifier.STATIC) != 0) {
                    arrAfterAllMethods.add(method);
                } else {
                    Logger.getGlobal().info("Annotation AfterAll only use on static method. Method [" + method.getName() + "] isn't static");
                }
            }

            if (method.isAnnotationPresent(Before.class)) { arrBeforeMethods.add(method); }
            if (method.isAnnotationPresent(After.class)) { arrAfterMethods.add(method); }
            if (method.isAnnotationPresent(Test.class)) { arrTestMethods.add(method); }
        }

        // аннотация BeforeAll для static-методов
        for (Method beforeAllMethod : arrBeforeAllMethods) {
            TestRunner.invokeMethod(beforeAllMethod, null);
        }

        // аннотация test
        for (Method testMethod : arrTestMethods) {
            try {
                Object object = testClass.getDeclaredConstructor().newInstance();

                // аннотация Before
                for (Method beforeMethod : arrBeforeMethods) {
                    TestRunner.invokeMethod(beforeMethod, object);
                }

                // аннотация Test
                TestRunner.invokeMethod(testMethod, object);

                // аннотация After
                for (Method afterMethod : arrAfterMethods) {
                    TestRunner.invokeMethod(afterMethod, object);
                }

            } catch (Exception e) {
                System.out.println(testMethod.getName() + " executed with error: " + e.getMessage());
            }
        }

        // аннотация BeforeAll для static-методов
        for (Method afterAllMethod : arrAfterAllMethods) {
            TestRunner.invokeMethod(afterAllMethod, null);
        }
    }
}
