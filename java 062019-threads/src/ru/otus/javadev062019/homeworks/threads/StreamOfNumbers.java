package ru.otus.javadev062019.homeworks.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class StreamOfNumbers {

    private static final int THREADS_COUNT = 2;
    private static final int MAIN_THREAD_SLEEP_TO_MILLISECOND = 3000;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 10;
    private static final int COUNT_DELTA = 1;

    private ConcurrentNavigableMap<String, Integer> map = new ConcurrentSkipListMap<>();
    private AtomicInteger delta = new AtomicInteger(1);
    private AtomicBoolean stop = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {

        StreamOfNumbers streamOfNumbers = new StreamOfNumbers();
        streamOfNumbers.go();
    }

    private void generateStream() {
        while (!stop.get()) {
            String keyMap = Thread.currentThread().getName();
            if (map.firstKey().equals(keyMap) && (map.get(keyMap) == map.get(map.lastKey()))) {
                valueAccumulator(keyMap, true);
            } else if (!keyMap.equals(map.firstKey()) && (map.get(keyMap) != map.lowerEntry(keyMap).getValue())) {
                valueAccumulator(keyMap, false);
            }
        }
    }

    private void valueAccumulator(String keyMap, boolean isFirstKey) {
        int count = map.get(keyMap);
        System.out.println(keyMap + ": " +count);
        if (isFirstKey) {
            if (count >= MAX_NUMBER) {
                delta.set(-1 * COUNT_DELTA);
            }
            if (count <= MIN_NUMBER) {
                delta.set(COUNT_DELTA);
            }
        }
        count = count + delta.get();
        map.merge(keyMap, count, (oldVal, newVal) -> newVal);
    }

    private void go() throws InterruptedException {

        List<Thread> threads = new ArrayList<>(THREADS_COUNT);
        for (int i = 0; i < THREADS_COUNT; i++) {
            Thread thread = new Thread(this::generateStream);
            thread.setDaemon(true);
            map.put(thread.getName(), MIN_NUMBER);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        sleep(MAIN_THREAD_SLEEP_TO_MILLISECOND);
        stop.set(true);
    }
}
