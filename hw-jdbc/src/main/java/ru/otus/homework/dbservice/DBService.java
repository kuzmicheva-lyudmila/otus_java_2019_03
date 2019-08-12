package ru.otus.homework.dbservice;

import java.sql.SQLException;

public interface DBService<T> {
    long create(T objectData);
    long update(T objectData);
    long createOrUpdate(T objectData);
    <T> T load(long id);
}
