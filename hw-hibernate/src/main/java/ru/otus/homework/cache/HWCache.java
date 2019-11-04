package ru.otus.homework.cache;

public interface HWCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(HWListener listener);

    void removeListener(HWListener listener);
}