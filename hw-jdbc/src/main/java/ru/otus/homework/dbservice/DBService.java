package ru.otus.homework.dbservice;

public interface DBService<T> {
    long create(T objectData);
    long update(T objectData);
    long createOrUpdate(T objectData);
    <T> T load(long id);
}
