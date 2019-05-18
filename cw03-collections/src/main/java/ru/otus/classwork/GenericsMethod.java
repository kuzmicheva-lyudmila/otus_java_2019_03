package ru.otus.classwork;

public class GenericsMethod {
    private <K,V> void print(K key, V val) {
        System.out.println("Key: " + key + ", val: " + val);
    }

    public static void main(String[] args) {
        GenericsMethod genericsMethod = new GenericsMethod();
        genericsMethod.print(1, "value1");
        genericsMethod.print(2, "value2");
    }
}
