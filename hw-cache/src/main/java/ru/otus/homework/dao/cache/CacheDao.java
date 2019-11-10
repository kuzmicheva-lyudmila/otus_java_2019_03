package ru.otus.homework.dao.cache;

public interface CacheDao<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(ListenerDao listener);

    void removeListener(ListenerDao listener);
}