package ru.otus.hw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MyList {
    private int size;
    private int step;

    public MyList(int size, int step) {
        this.size = size;
        this.step = step;
    }

    public void run() throws Exception {
        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < this.step; i++) {
            Integer[] integerArrayCopy = new Integer[this.size];
            Integer[] integerArrayDel = new Integer[(int)(this.size / 2) + 1];

            for (int k = 0; k < this.size; k++) {
                linkedList.addFirst(Integer.valueOf(k));
                if (k % 2 == 0) {
                    linkedList.removeLast();
                }
            }

            System.out.println("step = " + i + " size = " + linkedList.size());
            this.size = this.size * 2;

            Thread.sleep(500);
        }
    }
}
