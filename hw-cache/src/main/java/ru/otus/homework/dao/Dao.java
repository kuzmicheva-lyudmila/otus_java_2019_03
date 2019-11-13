package ru.otus.homework.dao;

import ru.otus.homework.models.User;

import java.util.List;

public interface Dao<T> {
    void create(T objectData);
    void update(T objectData);
    void delete(T objectData);
    <T> T load(long id);
    List<T> loadAll();
}
