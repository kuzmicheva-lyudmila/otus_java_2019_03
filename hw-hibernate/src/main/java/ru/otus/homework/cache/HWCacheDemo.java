package ru.otus.homework.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) throws InterruptedException {
        new HWCacheDemo().demo();
    }

    private void demo() throws InterruptedException {
        HWCache<Integer, Integer> cache = new MyCache<>();
        HWListener<Integer, Integer> listener1 =
                (key, value, action) -> logger.info("key: {}, value: {}, action: {}",  key, value, action);

        HWListener<Integer, Integer> listener2 =
                (key, value, action) -> logger.info("key: {}, value: {}, action: {}",  key, value, action);

        cache.addListener(listener1);
        cache.addListener(listener2);
        cache.put(1,1);

        logger.info("getValue:{}", cache.get(1));
        cache.removeListener(listener1);
        System.gc();
        Thread.sleep(100);
        cache.remove(1);
    }
}
