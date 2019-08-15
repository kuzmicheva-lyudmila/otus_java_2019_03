package ru.otus.homework;

import ru.otus.homework.dao.User;

public class MainClass {
    public static void main(String[] args) {
        User user = new User(1, "test", 10);
        System.out.println(user);
        System.out.println(User.getIdField());
    }
}
