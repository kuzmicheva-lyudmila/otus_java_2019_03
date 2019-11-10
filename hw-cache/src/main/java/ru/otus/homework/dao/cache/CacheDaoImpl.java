package ru.otus.homework.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.dao.UserDaoImpl;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;

public class CacheDaoImpl<K, V> implements CacheDao<K, V> {
    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<ListenerDao> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
        notifyListeners(key, null, "remove");
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notifyListeners(key, value, "get");
        return value;
    }

    @Override
    public void addListener(ListenerDao listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ListenerDao listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, String action) {
        for (ListenerDao listener : listeners) {
            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
