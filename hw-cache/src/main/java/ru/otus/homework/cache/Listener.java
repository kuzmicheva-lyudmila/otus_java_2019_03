package ru.otus.homework.cache;

public interface Listener<K, V> {
    void notify(K key, V value, String action);
}
