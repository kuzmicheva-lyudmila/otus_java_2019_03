package ru.otus.cw;

public class Person {

    @Length(min = 3, max = 8)
    String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
