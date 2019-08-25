package ru.otus.homework.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private long no;

    private String type;
    private double rest;
    private int level;

    public Account() {
    }

    public Account(String type, double rest, int level) {
        this.type = type;
        this.rest = rest;
        this.level = level;
    }

    public long getId() {
        return no;
    }

    @Override
    public String toString() {
        return "Account {" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                ", level=" + level +
                '}';
    }
}
