package ru.cft.hw_json;

import org.glassfish.json.JsonProviderImpl;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class Parser {

    public String parseToJson(Object object) {
        try {
            if (object == null) {
                return "null";
            } else if (object.getClass().isPrimitive() || isWrapperType(object.getClass()) || object.getClass() == String.class) {
                JsonValue jsonValue = parsePrimitiveType(object, object.getClass());
                return jsonValue.toString();
            } else if (object.getClass().isArray()) {
                JsonArrayBuilder jsonArrayBuilder = parseArrayTypeForObject(object);
                return jsonArrayBuilder.build().toString();
            } else if (object instanceof Collection) {
                Object arrayCollection = getArrayFromCollection((Collection) object);
                JsonArrayBuilder jsonArrayBuilder = parseArrayTypeForObject(arrayCollection);
                return jsonArrayBuilder.build().toString();
            } else {
                JsonObjectBuilder jsonObjectBuilder = parseObject(object);
                return jsonObjectBuilder.build().toString();
            }
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private JsonObjectBuilder parseObject(Object object) throws IllegalAccessException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Class clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        for (Field field : declaredFields) {
            if (field.get(object) != null) {
                parser(jsonObjectBuilder, field.getName(), field.get(object));
            }
        }
        return jsonObjectBuilder;
    }

    private JsonObjectBuilder parser(JsonObjectBuilder jsonObjectBuilder, String fieldName, Object fieldObject) throws IllegalAccessException {
        Class clazz = fieldObject.getClass();
        if (clazz.isArray()) {
            JsonArrayBuilder jsonArrayBuilder = parseArrayTypeForObject(fieldObject);
            jsonObjectBuilder.add(fieldName, jsonArrayBuilder);
        } else if (fieldObject instanceof Collection) {
            Object arrayCollection = getArrayFromCollection((Collection) fieldObject);
            JsonArrayBuilder jsonArrayBuilder = parseArrayTypeForObject(arrayCollection);
            jsonObjectBuilder.add(fieldName, jsonArrayBuilder);
        } else if (clazz.isPrimitive() || isWrapperType(clazz) || clazz == String.class) {
            jsonObjectBuilder.add(fieldName, parsePrimitiveType(fieldObject, clazz));
        } else {
            jsonObjectBuilder.add(fieldName, parseObject(fieldObject));
        }
        return jsonObjectBuilder;
    }

    private Object getArrayFromCollection(Collection fieldObject) {
        Collection collection = fieldObject;
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
        return arrayCollection;
    }

    private JsonArrayBuilder parseArrayTypeForObject(Object object) throws IllegalAccessException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Class clazz = (object == null) ? null : object.getClass();

        if (clazz == null) {
            return jsonArrayBuilder;
        }
        Class arrayClass = clazz.getComponentType();
        for (int i = 0; i < Array.getLength(object); i++) {
            Object itemArrayObject = Array.get(object, i);
            if (itemArrayObject == null) {
                jsonArrayBuilder.addNull();
            } else if (arrayClass.isPrimitive() || isWrapperType(arrayClass) || arrayClass == String.class) {
                jsonArrayBuilder.add(parsePrimitiveType(itemArrayObject, arrayClass));
            } else {
                jsonArrayBuilder.add(parseObject(itemArrayObject));
            }
        }
        return jsonArrayBuilder;
    }

    private JsonValue parsePrimitiveType(Object object, Class objectClass) {
        JsonValue jsonValue;
        if (objectClass == int.class
                || objectClass == Integer.class) {
            jsonValue = new JsonProviderImpl().createValue((int) object);
        } else if (objectClass == short.class
                || objectClass == Short.class) {
            int value = (short) object;
            jsonValue = Json.createValue(value);
        } else if (objectClass == byte.class
                || objectClass == Byte.class) {
            int value = (byte) object;
            jsonValue = Json.createValue(value);
        } else if (objectClass == long.class
                || objectClass == Long.class) {
            jsonValue = Json.createValue((long) object);
        } else if (objectClass == float.class
                || objectClass == Float.class) {
            double value = (float) object;
            jsonValue = Json.createValue(value);
        } else if (objectClass == double.class
                || objectClass == Double.class) {
            jsonValue = Json.createValue((double) object);
        } else if (objectClass == char.class
                || objectClass == Character.class
                || objectClass == String.class) {
            jsonValue = Json.createValue(String.valueOf(object));
        } else if ((objectClass == boolean.class || objectClass == Boolean.class)
                && (boolean) object == true) {
            jsonValue = JsonValue.TRUE;
        } else if ((objectClass == boolean.class || objectClass == Boolean.class)
                && (boolean) object == false) {
            jsonValue = JsonValue.FALSE;
        } else {
            jsonValue = JsonValue.NULL;
        }
        return jsonValue;
    }

    private boolean isWrapperType(Class clazz) {
        if (clazz == Integer.class
                || clazz == Long.class
                || clazz == Short.class
                || clazz == Byte.class
                || clazz == Float.class
                || clazz == Double.class
                || clazz == Character.class
                || clazz == Boolean.class) {
            return true;
        } else {
            return false;
        }
    }
}
