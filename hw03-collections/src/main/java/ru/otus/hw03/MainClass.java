package ru.otus.hw03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        List<Integer> myDIYArrayList = new DIYarrayList<>();

        Collections.addAll(myDIYArrayList, 10, 3, 44, 6, 5, 7, 1, 3, 88, 9, 10, 12, 14, 1, 19, 34, 12, 54, 1, 44, 77, 66, 3);
        System.out.println(myDIYArrayList.toString());

        List<Integer> newList = new ArrayList<>();
        for (int i = 1; i<=20; i++)
            newList.add(i);

        Collections.copy(myDIYArrayList, newList);
        System.out.println(myDIYArrayList.toString());

        Collections.sort(myDIYArrayList);
        System.out.println(myDIYArrayList.toString());
    }
}
