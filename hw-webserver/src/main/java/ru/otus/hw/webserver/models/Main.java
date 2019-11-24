package ru.otus.hw.webserver.models;


public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setName("kuzmicheva Lyuda");
        user.setAge(9);
        user.setAddress(new AddressDataSet());
    }
}
