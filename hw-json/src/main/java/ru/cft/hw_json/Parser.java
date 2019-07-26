package ru.cft.hw_json;

import com.sun.javafx.collections.MappingChange;
import sun.plugin2.message.JavaScriptBaseMessage;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.ObjectInputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class Parser {

    public String parseToJson(Object object) {
        try {
            JsonObjectBuilder jsonObjectBuilder = parseObject(object);
            return jsonObjectBuilder.build().toString();
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private JsonObjectBuilder parseObject(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Field[] declaredFields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        for (Field field : declaredFields) {
            parser(jsonObjectBuilder, field.getType(), field.getName(), field.get(object));
        }
        return jsonObjectBuilder;
    }

    private JsonObjectBuilder parser(JsonObjectBuilder jsonObjectBuilder, Class clazz, String fieldName, Object fieldObject) throws IllegalAccessException {
        if (clazz.isArray()) {
            JsonArrayBuilder jsonArrayBuilder = parseArrayTypeForObject(clazz, fieldObject);
            jsonObjectBuilder.add(fieldName, jsonArrayBuilder);
        } else if (fieldObject instanceof Collection) {
            Collection collection = (Collection) fieldObject;
            Class classArrayCollection = null;
            Object arrayCollection = null;
            if (collection.size() > 0) {
                classArrayCollection = collection.iterator().next().getClass();
            }
            if (classArrayCollection != null) {
                arrayCollection = Array.newInstance(classArrayCollection, collection.size());
                int i = 0;
                for (Object o : collection) {
                    Array.set(arrayCollection, i, o);
                    i++;
                }
            }
            JsonArrayBuilder jsonArrayBuilder = parseArrayTypeForObject((arrayCollection == null) ? null : arrayCollection.getClass(), arrayCollection);
            jsonObjectBuilder.add(fieldName, jsonArrayBuilder);
        } else if (fieldObject instanceof Map) {
            System.out.println(fieldName + " map");
        } else if (clazz.isPrimitive()) {
            parsePrimitiveTypeForObject(jsonObjectBuilder, clazz.getTypeName(), fieldName, fieldObject);
        } else if (clazz == String.class) {
            jsonObjectBuilder.add(fieldName, String.valueOf(fieldObject));
        } else if (isWrapperType(clazz)) {
            parsePrimitiveTypeForObject(jsonObjectBuilder, mappingWrapperTypeToPrimitive(clazz.getSimpleName()), fieldName, fieldObject);
        } else {
            jsonObjectBuilder.add(fieldName, parseObject(fieldObject));
        }
        return jsonObjectBuilder;
    }

    private JsonArrayBuilder parseArrayTypeForObject(Class clazz, Object object) throws IllegalAccessException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        if (clazz == null) {
            return jsonArrayBuilder;
        }
        Class arrayClass = clazz.getComponentType();
        for (int i = 0; i < Array.getLength(object); i++) {
            Object itemArrayObject = Array.get(object, i);
            if (arrayClass.isPrimitive()) {
                parsePrimitiveTypeForArray(itemArrayObject, jsonArrayBuilder, arrayClass.getTypeName());
            } else if (isWrapperType(arrayClass)) {
                parsePrimitiveTypeForArray(itemArrayObject, jsonArrayBuilder, mappingWrapperTypeToPrimitive(arrayClass.getSimpleName()));
            }
            else if (arrayClass == String.class) {
                jsonArrayBuilder.add(String.valueOf(itemArrayObject));

            } else {
                jsonArrayBuilder.add(parseObject(itemArrayObject));
            }
        }
        return jsonArrayBuilder;
    }

    private String mappingWrapperTypeToPrimitive(String wrapperType) {
        String result = "";
        switch (wrapperType) {
            case "Integer":
                result = "int";
                break;
            case "Long":
                result = "long";
                break;
            case "Short":
                result = "short";
                break;
            case "Byte":
                result = "byte";
                break;
            case "Float":
                result = "float";
                break;
            case "Double":
                result = "double";
                break;
            case "Character":
                result = "char";
                break;
            case "Boolean":
                result = "boolean";
                break;
        }
        return result;
    }

    private boolean isWrapperType(Class clazz) {
        boolean result;
        switch (clazz.getSimpleName()) {
            case "Integer":
            case "Long":
            case "Short":
            case "Byte":
            case "Float":
            case "Double":
            case "Character":
            case "Boolean":
                result = true;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

    private void parsePrimitiveTypeForArray(Object object, JsonArrayBuilder jsonArrayBuilder, String typeName) {
        switch (typeName) {
            case "int":
                jsonArrayBuilder.add((int) object);
                break;
            case "long":
                jsonArrayBuilder.add((long) object);
                break;
            case "short":
                jsonArrayBuilder.add((short) object);
                break;
            case "byte":
                jsonArrayBuilder.add((byte) object);
                break;
            case "float":
                jsonArrayBuilder.add((float) object);
                break;
            case "double":
                jsonArrayBuilder.add((double) object);
                break;
            case "char":
                jsonArrayBuilder.add(String.valueOf(object));
                break;
            case "boolean":
                jsonArrayBuilder.add((boolean) object);
                break;
        }
    }

    private void parsePrimitiveTypeForObject(JsonObjectBuilder jsonObjectBuilder, String typeName, String fieldName, Object object) {
        switch (typeName) {
            case "int":
                jsonObjectBuilder.add(fieldName, (int) object);
                break;
            case "long":
                jsonObjectBuilder.add(fieldName, (long) object);
                break;
            case "short":
                jsonObjectBuilder.add(fieldName, (short) object);
                break;
            case "byte":
                jsonObjectBuilder.add(fieldName, (byte) object);
                break;
            case "float":
                jsonObjectBuilder.add(fieldName, (float) object);
                break;
            case "double":
                jsonObjectBuilder.add(fieldName, (double) object);
                break;
            case "char":
                jsonObjectBuilder.add(fieldName, String.valueOf(object));
                break;
            case "boolean":
                jsonObjectBuilder.add(fieldName, (boolean) object);
                break;
        }
    }
}
