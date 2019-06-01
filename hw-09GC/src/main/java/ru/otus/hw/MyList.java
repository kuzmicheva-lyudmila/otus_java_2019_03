package ru.otus.hw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyList {
    private int size;
    private int step;

    public MyList(int size, int step) {
        this.size = size;
        this.step = step;
    }

    public void run() throws Exception {
        List<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < this.step; i++) {
            Integer[] integerArrayCopy = new Integer[this.size];
            Integer[] integerArrayDel = new Integer[(int)(this.size / 2) + 1];

            for (int k = 0; k < integerArrayCopy.length; k++) {
                integerArrayCopy[k] = k;
                if (k < integerArrayDel.length) {
                    integerArrayDel[k] = k;
                }
            }

            List<Integer> integerArrayListCopy = new ArrayList<Integer>(Arrays.asList(integerArrayCopy));
            arrayList.addAll(integerArrayListCopy);

            List<Integer> integerArrayListDel = new ArrayList<Integer>(Arrays.asList(integerArrayDel));
            arrayList.removeAll(integerArrayListDel);

            System.out.println("step = " + i + " size = " + arrayList.size());
            this.size = this.size * 2;

            Thread.sleep(500);
        }
    }
}
