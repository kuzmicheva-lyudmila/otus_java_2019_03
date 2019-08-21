package ru.otus.homework.executor;

import ru.otus.homework.dao.Id;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {
    private static final Package COMMON_PACKAGE_FOR_CLASSES = Id.class.getPackage();
    private static Map<Class, MetaInfo> mapForClassMetaInfo = new HashMap<>();

    private final Connection connection;

    public JdbcTemplateImpl(Connection connection, Class clazz) {
        this.connection = connection;
        if (!mapForClassMetaInfo.containsKey(clazz)) {
            mapForClassMetaInfo.put(clazz, new MetaInfo(clazz));
        }
    }

    @Override
    public long create(T objectData) throws SQLException, ReflectiveOperationException {
        Class objectClass = objectData.getClass();
        String idFieldName = mapForClassMetaInfo.get(objectClass).getIdField();
        long idFieldValue = 0;
        if (idFieldName != null) {
            Savepoint savePoint = this.connection.setSavepoint("savePointName");
            String command = mapForClassMetaInfo.get(objectClass).getInsertCommand();
            try (PreparedStatement pst = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
                setParametersOnCommand(objectData, objectClass, pst);
                pst.executeUpdate();
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    rs.next();
                    idFieldValue = rs.getInt(1);
                }
                if (idFieldValue > 0) {
                    connection.commit();
                }
            } catch (SQLException | ReflectiveOperationException e) {
                connection.rollback(savePoint);
                System.out.println(e.getMessage());
                throw e;
            }
        }
        return idFieldValue;
    }

    @Override
    public long update(T objectData) throws SQLException, ReflectiveOperationException {
        Class objectClass = objectData.getClass();
        String idFieldName = mapForClassMetaInfo.get(objectClass).getIdField();
        long idFieldValue = 0;
        if (idFieldName != null) {
            Savepoint savePoint = this.connection.setSavepoint("savePointName");
            String command = mapForClassMetaInfo.get(objectClass).getUpdateCommand();
            try (PreparedStatement pst = connection.prepareStatement(command)) {
                int counter = setParametersOnCommand(objectData, objectClass, pst);
                Field idField = objectClass.getDeclaredField(idFieldName);
                idField.setAccessible(true);
                pst.setObject(++counter, idField.getLong(objectData));
                int isUpdated = pst.executeUpdate();
                idFieldValue = (isUpdated > 0) ? idField.getLong(objectData) : 0;
                if (idFieldValue > 0) {
                    connection.commit();
                }
            } catch (SQLException | ReflectiveOperationException  e) {
                this.connection.rollback(savePoint);
                System.out.println(e.getMessage());
                throw e;
            }
        }
        return idFieldValue;
    }

    @Override
    public long createOrUpdate(T objectData) throws SQLException, ReflectiveOperationException {
        long idFieldValue = update(objectData);
        if (idFieldValue == 0) {
            long idObject = create(objectData);
            idFieldValue = (idObject > 0) ? idObject : 0;
        }
        return idFieldValue;
    }

    @Override
    public <T> T load(long id, Class<T> objectClass) throws SQLException, ReflectiveOperationException {
        T objectData = null;
        String idFieldName = mapForClassMetaInfo.get(objectClass).getIdField();
        if (idFieldName != null) {
            String command = mapForClassMetaInfo.get(objectClass).getSelectCommand();
            try (PreparedStatement pst = this.connection.prepareStatement(command)) {
                pst.setLong(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        objectData = objectClass.newInstance();
                        for (String fieldName : mapForClassMetaInfo.get(objectClass).getClassFields()) {
                            Field field = objectClass.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            field.set(objectData, parseType(field.getType(), rs, fieldName));
                        }
                        Field field = objectClass.getDeclaredField(idFieldName);
                        field.setAccessible(true);
                        field.set(objectData, rs.getLong(idFieldName));
                    }
                } catch (IllegalAccessException | InstantiationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return objectData;
    }

    private int setParametersOnCommand(T objectData, Class objectClass, PreparedStatement pst) throws SQLException, ReflectiveOperationException {
        int counter = 0;
        for (String fieldName : mapForClassMetaInfo.get(objectClass).getClassFields()) {
            Field field = objectClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            pst.setObject(++counter, field.get(objectData));
        }
        return counter;
    }

    private Object parseType(Class objectClass, ResultSet resultSet, String fieldName) throws SQLException {
        if (objectClass == int.class
                || objectClass == Integer.class) {
            return resultSet.getInt(fieldName);
        } else if (objectClass == short.class
                || objectClass == Short.class) {
            return resultSet.getShort(fieldName);
        } else if (objectClass == byte.class
                || objectClass == Byte.class) {
            return resultSet.getByte(fieldName);
        } else if (objectClass == long.class
                || objectClass == Long.class) {
            return resultSet.getLong(fieldName);
        } else if (objectClass == float.class
                || objectClass == Float.class) {
            return resultSet.getFloat(fieldName);
        } else if (objectClass == double.class
                || objectClass == Double.class) {
            return resultSet.getDouble(fieldName);
        } else if (objectClass == char.class
                || objectClass == Character.class
                || objectClass == String.class) {
            return resultSet.getString(fieldName);
        } else if (objectClass == boolean.class
                || objectClass == Boolean.class) {
            return resultSet.getBoolean(fieldName);
        } else {
            return resultSet.getObject(fieldName);
        }
    }
}