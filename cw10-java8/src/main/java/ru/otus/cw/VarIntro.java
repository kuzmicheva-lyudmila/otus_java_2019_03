package ru.otus.cw;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class VarIntro {
    public static void main(String[] args) throws IOException {
        //new VarIntro().test(5);

        int[] i = new int[10];
        VarIntro.test(i);
        for (int k = 0; k < 10; k++) {
            System.out.println(i[k]);
        }
        ArrayList
    }

    public static void test(int[] i) {
        i[0] = 0;
        i[1] = 1;
    }

    // private var test (var val) throws IOException // ошибка компиляции
    private String test(int val) throws IOException {
        var example = "ValueStr"; // String
        example = "newString";
        // example = 5; нельзя изменить тип
        // var error = null; ошибка, тип неопределен

        try (InputStream is = new FileInputStream("lines.txt")) {

        }

        // FileInpitStream
        try (var is = new FileInputStream("lines.txt")) {

        }

        //List<String> list = Arrays.asList("1", "2", "3");
        var list = Arrays.asList("1", "2", "3");
        for (var str: list) {
            System.out.println(str);
        }

        // var nameList = new ArrayList<>(); не понятен тип
        var StringList = new ArrayList<String>(); // тут тип определен

        var testString = "StringVal";
        if (testString instanceof String) {
            System.out.println("testString is String");
        }

        return "";
    }

}
