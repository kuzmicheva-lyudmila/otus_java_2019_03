package ru.otus.homework.cache;

public interface HWListener<K, V> {
    void notify(K key, V value, String action);
}
