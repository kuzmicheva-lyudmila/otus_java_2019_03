package ru.otus.homework.executor;

import ru.otus.homework.dao.Id;

import java.lang.reflect.Field;

public class MetaCommand {
    public static String getIdFieldOnTable(Class objectClass) {
        for (Field field : objectClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return null;
    }

    public static String getInsertCommand(String idColumnName, Class objectClass) {
        if (objectClass.getDeclaredFields().length > 1) {
            StringBuilder listColumnNames = new StringBuilder();
            StringBuilder listParameters = new StringBuilder();
            for (Field field : objectClass.getDeclaredFields()) {
                if (!field.getName().equals(idColumnName)) {
                    listColumnNames.append(field.getName());
                    listColumnNames.append(",");
                    listParameters.append("?,");
                }
            }
            listColumnNames.setLength(listColumnNames.length() - 1);
            listParameters.setLength(listParameters.length() - 1);
            return String.format("insert into %s(%s) values (%s)", objectClass.getSimpleName(), listColumnNames.toString(), listParameters.toString());
        } else {
            return null;
        }
    }

    public static String getUpdateCommand(String idColumnName, Class objectClass) {
        if (objectClass.getDeclaredFields().length > 1) {
            StringBuilder listColumnNames = new StringBuilder();
            for (Field field : objectClass.getDeclaredFields()) {
                if (!field.getName().equals(idColumnName)) {
                    listColumnNames.append(field.getName());
                    listColumnNames.append("=?,");
                }
            }
            listColumnNames.setLength(listColumnNames.length() - 1);
            return String.format("update %s set %s where %s=?", objectClass.getSimpleName(), listColumnNames.toString(), idColumnName);
        } else {
            return null;
        }
    }

    public static String getSelectCommand(String idColumnName, Class objectClass) {
        return String.format("select * from %s where %s = ?", objectClass.getSimpleName(), idColumnName);
    }
}
