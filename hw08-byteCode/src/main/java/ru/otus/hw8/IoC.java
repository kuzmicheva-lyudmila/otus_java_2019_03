package ru.otus.hw8;

import ru.otus.hw8.annotations.Log;
import ru.otus.hw8.loggins.FirstLogging;
import ru.otus.hw8.loggins.SecondLogging;

import java.lang.reflect.*;
import java.util.ArrayList;

public class IoC {
    static Object createTestClass(Class clazz) throws Exception {
        Constructor<?> constructor = clazz.getConstructor();
        Object object = constructor.newInstance();
        InvocationHandler handler = new TestInvocationHandler(object);
        return Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{FirstLogging.class, SecondLogging.class}, handler);
    }

    static class TestInvocationHandler implements InvocationHandler {
        private final Object logObject;
        private final Class logClass;
        private final ArrayList<Method> methodsWithLog;
        private final boolean isClassNeedLogging;

        TestInvocationHandler(Object logObject) {
            this.logObject = logObject;
            this.logClass = logObject.getClass();
            this.methodsWithLog = new ArrayList<>();

            Method[] methods = this.logObject.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].isAnnotationPresent(Log.class)) {
                    this.methodsWithLog.add(methods[i]);
                }
            }

            this.isClassNeedLogging = (this.methodsWithLog.size() > 0) ? true: false;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (this.isClassNeedLogging &&
                    this.methodsWithLog.contains(logClass.getDeclaredMethod(method.getName(), method.getParameterTypes())))
            {
                System.out.print(">>> EXECUTED METHOD: " + method.getName() + " {");

                Parameter[] parameters = method.getParameters();
                if (parameters != null) {
                    for (int i = 0; i < parameters.length; i++) {
                        System.out.print(parameters[i].getName() + " = [" + args[i].toString() + "] ");
                    }
                }

                System.out.println("}");
            }

            return method.invoke(logObject, args);
        }
    }


}
