package ru.otus.cw;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DemoApplication {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Person person = new Person();
        person.setName("Va");

        Class<LengthValidator> validatorClass = LengthValidator.class;
        Constructor<LengthValidator> constructor = validatorClass.getConstructor();
        LengthValidator lengthValidator = constructor.newInstance();

        lengthValidator.validate(person);
    }
}
