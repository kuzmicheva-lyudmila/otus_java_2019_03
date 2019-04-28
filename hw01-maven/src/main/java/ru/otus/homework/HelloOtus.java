package ru.otus.homework;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class HelloOtus {

    public static void main(String[] args) {
        String[] words = new String[] {"one", "one", "two", "three"};
        Multiset<String> counts = HashMultiset.create();
        for (String word : words) {
            counts.add(word);
        }
        System.out.println(counts.toString());
    }
}
