package ru.otus.homework.dao.cache;

public interface ListenerDao<K, V> {
    void notify(K key, V value, String action);
}
