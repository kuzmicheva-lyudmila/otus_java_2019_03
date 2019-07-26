package ru.cft.hw_json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ru.cft.hw_json.classExamples.FirstSerializeClass;
import ru.cft.hw_json.classExamples.SecondSerializeClass;

import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        Gson gson = new Gson();

        List<String> listOfString = new ArrayList<>();
        listOfString.add("Hello");
        listOfString.add("world!!!");

        SecondSerializeClass secondSerializeClass = new SecondSerializeClass(2, "world", 'h');
        FirstSerializeClass firstSerializeClass = new FirstSerializeClass(false, new SecondSerializeClass[]{new SecondSerializeClass(2, "world", 'a'), new SecondSerializeClass(4, "hello", 'b')}, listOfString);

        FirstSerializeClass objFirst = gson.fromJson(new Parser().parseToJson(firstSerializeClass), FirstSerializeClass.class);
        System.out.println(objFirst);

        String s = "{\"valueInt\":2,\"valueString\":\"world\",\"valueChar\":\"h\"";
        try {
            SecondSerializeClass objSecond = gson.fromJson(s, SecondSerializeClass.class);
            System.out.println(objSecond);
        } catch (JsonSyntaxException e) {
            System.out.println("error");
        }
        System.out.println(new Parser().parseToJson(secondSerializeClass));
    }
}
