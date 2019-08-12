package ru.otus.homework.executor;

import java.lang.reflect.Field;
import java.sql.*;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    private final Connection connection;

    public JdbcTemplateImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(T objectData) throws SQLException, IllegalAccessException {
        Class objectClass = objectData.getClass();
        String idFieldName = MetaCommand.getIdFieldOnTable(objectClass);
        long idFieldValue = 0;
        if (idFieldName != null) {
            Savepoint savePoint = this.connection.setSavepoint("savePointName");
            String command = MetaCommand.getInsertCommand(idFieldName, objectClass);
            try (PreparedStatement pst = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
                int counter = 0;
                for (int idx = 0; idx < objectClass.getDeclaredFields().length; idx++) {
                    Field field = objectClass.getDeclaredFields()[idx];
                    if (!objectClass.getDeclaredFields()[idx].getName().equals(idFieldName)) {
                        field.setAccessible(true);
                        pst.setObject(++counter, field.get(objectData));
                    }
                }
                pst.executeUpdate();
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    rs.next();
                    idFieldValue = rs.getInt(1);
                }
            } catch (SQLException | IllegalAccessException e) {
                connection.rollback(savePoint);
                System.out.println(e.getMessage());
                throw e;
            }
        }
        return idFieldValue;
    }

    @Override
    public long update(T objectData) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Class objectClass = objectData.getClass();
        String idFieldName = MetaCommand.getIdFieldOnTable(objectClass);
        long idFieldValue = 0;
        if (idFieldName != null) {
            Savepoint savePoint = this.connection.setSavepoint("savePointName");
            String command = MetaCommand.getUpdateCommand(idFieldName, objectClass);
            try (PreparedStatement pst = connection.prepareStatement(command)) {
                int counter = 0;
                for (int idx = 0; idx < objectClass.getDeclaredFields().length; idx++) {
                    Field field = objectClass.getDeclaredFields()[idx];
                    if (!objectClass.getDeclaredFields()[idx].getName().equals(idFieldName)) {
                        field.setAccessible(true);
                        pst.setObject(++counter, field.get(objectData));
                    }
                }
                Field idField = objectClass.getDeclaredField(idFieldName);
                idField.setAccessible(true);
                pst.setObject(++counter, idField.getLong(objectData));
                int isUpdated = pst.executeUpdate();
                idFieldValue = (isUpdated > 0) ? idField.getLong(objectData) : 0;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                this.connection.rollback(savePoint);
                System.out.println(e.getMessage());
                throw e;
            }
        }
        return idFieldValue;
    }

    @Override
    public long createOrUpdate(T objectData) throws SQLException, NoSuchFieldException, IllegalAccessException {
        long idFieldValue = update(objectData);
        if (idFieldValue == 0) {
            long idObject = create(objectData);
            idFieldValue = (idObject > 0) ? idObject : 0;
        }
        return idFieldValue;
    }

    @Override
    public <T> T load(long id, Class<T> objectClass) throws SQLException {
        T objectData = null;
        String idFieldName = MetaCommand.getIdFieldOnTable(objectClass);
        if (idFieldName != null) {
            String command = MetaCommand.getSelectCommand(idFieldName, objectClass);
            try (PreparedStatement pst = this.connection.prepareStatement(command)) {
                pst.setLong(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        objectData = objectClass.newInstance();
                        for (int idx = 0; idx < objectClass.getDeclaredFields().length; idx++) {
                            Field field = objectClass.getDeclaredFields()[idx];
                            field.setAccessible(true);
                            field.set(objectData, parseType(field.getType(), rs, idx + 1));
                        }
                    }
                } catch (IllegalAccessException | InstantiationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return objectData;
    }

    private Object parseType(Class objectClass, ResultSet resultSet, int index) throws SQLException {
        if (objectClass == int.class
                || objectClass == Integer.class) {
            return resultSet.getInt(index);
        } else if (objectClass == short.class
                || objectClass == Short.class) {
            return resultSet.getShort(index);
        } else if (objectClass == byte.class
                || objectClass == Byte.class) {
            return resultSet.getByte(index);
        } else if (objectClass == long.class
                || objectClass == Long.class) {
            return resultSet.getLong(index);
        } else if (objectClass == float.class
                || objectClass == Float.class) {
            return resultSet.getFloat(index);
        } else if (objectClass == double.class
                || objectClass == Double.class) {
            return resultSet.getDouble(index);
        } else if (objectClass == char.class
                || objectClass == Character.class
                || objectClass == String.class) {
            return resultSet.getString(index);
        } else if (objectClass == boolean.class
                || objectClass == Boolean.class) {
            return resultSet.getBoolean(index);
        } else {
            return resultSet.getObject(index);
        }
    }
}