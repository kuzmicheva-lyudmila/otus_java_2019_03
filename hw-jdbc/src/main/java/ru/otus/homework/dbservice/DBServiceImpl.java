package ru.otus.homework.dbservice;

import ru.otus.homework.executor.JdbcTemplate;
import ru.otus.homework.executor.JdbcTemplateImpl;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBServiceImpl<T> implements DBService<T> {
    private final DataSource dataSource;
    private final Class<T> objectClass;

    public DBServiceImpl(DataSource dataSource, Class<T> objectClass) {
        this.dataSource = dataSource;
        this.objectClass = objectClass;
    }

    @Override
    public long create(T objectData) {
        try (Connection connection = dataSource.getConnection()) {
            JdbcTemplate<T> executor = new JdbcTemplateImpl<>(connection);
            long objectId = executor.create(objectData);
            return objectId;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public long update(T objectData) {
        try (Connection connection = dataSource.getConnection()) {
            JdbcTemplate<T> executor = new JdbcTemplateImpl<>(connection);
            long objectId = executor.update(objectData);
            return objectId;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public long createOrUpdate(T objectData) {
        try (Connection connection = dataSource.getConnection()) {
            JdbcTemplate<T> executor = new JdbcTemplateImpl<>(connection);
            long objectId = executor.createOrUpdate(objectData);
            return objectId;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> T load(long id) {
        try (Connection connection = dataSource.getConnection()) {
            JdbcTemplate<T> executor = new JdbcTemplateImpl<>(connection);
            T objectData = (T) executor.load(id, objectClass);
            return objectData;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
