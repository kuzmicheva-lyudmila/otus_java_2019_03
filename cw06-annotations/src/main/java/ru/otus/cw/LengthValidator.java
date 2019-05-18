package ru.otus.cw;

import java.lang.reflect.Field;

public class LengthValidator {
    public boolean validate(Object object) throws IllegalAccessException{
        System.out.println("length validator");
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!field.isAnnotationPresent(Length.class)) {
                continue;
            }
            try {
                field.setAccessible(true);
                System.out.println("Annotation @Lenth present");
                Length length = field.getAnnotation(Length.class);
                int min = length.min();
                int max = length.max();

                String value = (String) field.get(object);
                if (max < value.length() || value.length() < min) {
                    System.out.println("Length is invalid: " + value.length());
            }
            } finally {
                field.setAccessible(false);
            }
        }
        return true;
    }
}
