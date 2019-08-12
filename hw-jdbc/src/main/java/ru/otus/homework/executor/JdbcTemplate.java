package ru.otus.homework.executor;

import java.sql.SQLException;

public interface JdbcTemplate<T> {
    long create(T objectData) throws SQLException, IllegalAccessException;;
    long update(T objectData) throws SQLException, NoSuchFieldException, IllegalAccessException;;
    long createOrUpdate(T objectData) throws SQLException, NoSuchFieldException, IllegalAccessException;
    <T> T load(long id, Class<T> clazz) throws SQLException;
}
