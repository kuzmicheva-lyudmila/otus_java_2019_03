package ru.cft.hw_json.classExamples;

import java.util.Map;

public class SecondSerializeClass{
    private Integer valueInt;
    private String valueString;
    private char valueChar;

    public SecondSerializeClass(int valueInt, String valueString, char valueChar) {
        this.valueInt = Integer.valueOf(valueInt);
        this.valueString = valueString;
        this.valueChar = valueChar;
    }
}
