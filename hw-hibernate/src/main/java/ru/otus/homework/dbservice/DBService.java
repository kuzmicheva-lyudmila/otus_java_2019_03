package ru.otus.homework.dbservice;

import ru.otus.homework.dao.Dao;

public interface DBService<T> {
    void create(T objectData);
    void update(T objectData);
    void delete(T objectData);
    <T> T load(long id);
}
