package ru.otus.homework.cache;

import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HWCache<K, V> {
    Map<K, V> cache = new WeakHashMap<>();
    List<WeakReference<HWListener>> listeners = new ArrayList<>();

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
    public void addListener(HWListener listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HWListener listener) {
        Optional<WeakReference<HWListener>> wrListener = listeners.stream()
                .filter(listenerWeakReference -> listenerWeakReference.get().equals(listener))
                .findFirst();

        if (wrListener.isPresent()) {
            boolean result = listeners.remove(wrListener.get());
        }
    }

    private void notifyListeners(K key, V value, String action) {
        for (WeakReference<HWListener> listener : listeners) {
            listener.get().notify(key, value, action);
        }
    }
}
