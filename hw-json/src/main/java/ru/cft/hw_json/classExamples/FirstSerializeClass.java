package ru.cft.hw_json.classExamples;

import java.util.List;

public class FirstSerializeClass {
    private boolean valueBoolean;
    private SecondSerializeClass[] arrayObjects;
    private List<String> listString;

    public FirstSerializeClass(boolean valueBoolean, SecondSerializeClass[] arrayObjects, List<String> listString) {
        this.valueBoolean = valueBoolean;
        this.arrayObjects = arrayObjects;
        this.listString = listString;
    }
}
