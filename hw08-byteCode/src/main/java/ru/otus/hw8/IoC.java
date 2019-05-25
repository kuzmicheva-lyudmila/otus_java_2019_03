package ru.otus.hw8;

import ru.otus.hw8.annotations.Log;
import ru.otus.hw8.loggins.Loggins1;
import ru.otus.hw8.loggins.Loggins2;

import java.lang.reflect.*;

public class IoC {
    static Object createTestClass(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = clazz.getConstructor();
        Object object = constructor.newInstance();
        InvocationHandler handler = new TestInvocationHandler(object);
        return Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{Loggins1.class, Loggins2.class}, handler);
    }

    static class TestInvocationHandler implements InvocationHandler {
        private final Object logClass;

        TestInvocationHandler(Object logClass) {
            this.logClass = logClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method methodFromObject = logClass.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

            if (methodFromObject.isAnnotationPresent(Log.class)) {
                System.out.print(">>> EXECUTED METHOD: " + method.getName() + " {");

                Parameter[] parameters = method.getParameters();
                if (parameters != null) {
                    for (int i = 0; i < parameters.length; i++) {
                        System.out.print(parameters[i].getName() + " = [" + args[i].toString() + "] ");
                    }
                }

                System.out.println("}");
            }

            return method.invoke(logClass, args);
        }
    }


}
