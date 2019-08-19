package ru.otus.homework.executor;

import ru.otus.homework.dao.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MetaInfo {
    private Class thisClass;

    private String idField;
    private List<String> classFields;
    private String insertCommand;
    private String updateCommand;
    private String selectCommand;

    public MetaInfo(Class clazz) {
        thisClass = clazz;
        idField = setIdFieldOnTable();
        classFields = fillClassFields();
        insertCommand = setInsertCommand();
        updateCommand = setUpdateCommand();
        selectCommand = setSelectCommand();
    }

    public String getIdField() {
        return idField;
    }

    public List<String> getClassFields() {
        return classFields;
    }

    public String getInsertCommand() {
        return insertCommand;
    }

    public String getUpdateCommand() {
        return updateCommand;
    }

    public String getSelectCommand() {
        return selectCommand;
    }

    private String setIdFieldOnTable() {
        for (Field field : thisClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return null;
    }

    private List<String> fillClassFields() {
        List classFields = new ArrayList();
        for (Field field : thisClass.getDeclaredFields()) {
            if (!field.getName().equals(idField)) {
                classFields.add(field.getName());
            }
        }
        return classFields;
    }

    private String setInsertCommand() {
        if (classFields.size() > 1) {
            StringBuilder listColumnNames = new StringBuilder();
            StringBuilder listParameters = new StringBuilder();
            for (String field : classFields) {
                if (!field.equals(idField)) {
                    listColumnNames.append(field);
                    listColumnNames.append(",");
                    listParameters.append("?,");
                }
            }
            listColumnNames.setLength(listColumnNames.length() - 1);
            listParameters.setLength(listParameters.length() - 1);
            return String.format("insert into %s(%s) values (%s)", thisClass.getSimpleName(), listColumnNames.toString(), listParameters.toString());
        } else {
            return null;
        }
    }

    private String setUpdateCommand() {
        if (thisClass.getDeclaredFields().length > 1) {
            StringBuilder listColumnNames = new StringBuilder();
            for (String field : classFields) {
                if (!field.equals(idField)) {
                    listColumnNames.append(field);
                    listColumnNames.append("=?,");
                }
            }
            listColumnNames.setLength(listColumnNames.length() - 1);
            return String.format("update %s set %s where %s=?", thisClass.getSimpleName(), listColumnNames.toString(), idField);
        } else {
            return null;
        }
    }

    private String setSelectCommand() {
        return String.format("select * from %s where %s = ?", thisClass.getSimpleName(), idField);
    }
}
