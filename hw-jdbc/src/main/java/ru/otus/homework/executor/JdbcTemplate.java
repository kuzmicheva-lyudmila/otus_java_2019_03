package ru.otus.homework.executor;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface JdbcTemplate<T> {
    long create(T objectData) throws SQLException, ReflectiveOperationException;
    long update(T objectData) throws SQLException, ReflectiveOperationException;
    long createOrUpdate(T objectData) throws SQLException, ReflectiveOperationException;
    <T> T load(long id, Class<T> clazz) throws SQLException, ReflectiveOperationException;
}
