package ru.otus.homework.dao;

public interface Dao<T> {
    void create(T objectData);
    void update(T objectData);
    void delete(T objectData);
    <T> T load(long id);
}
