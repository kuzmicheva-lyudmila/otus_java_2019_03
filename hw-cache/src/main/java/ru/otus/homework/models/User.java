package ru.otus.homework.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private int age;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneDataSet> phones;

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "User {" +
                "id = " + id +
                ", Name = '" + name + '\'' +
                ", Age = '" + age + '\'' +
                ", Address = " + address +
                ", Phones = " + Arrays.toString(phones.toArray()) +
                "}";
    }
}
