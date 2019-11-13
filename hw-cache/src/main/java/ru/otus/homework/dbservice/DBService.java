package ru.otus.homework.dbservice;

public interface DBService<T> {
    void create(T objectData);
    void update(T objectData);
    void delete(T objectData);
    <T> T load(long id);
}
