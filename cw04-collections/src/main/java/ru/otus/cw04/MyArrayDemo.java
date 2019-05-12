package ru.otus.cw04;

import java.util.ArrayList;
import java.util.List;

public class MyArrayDemo {
    public static void main(String[] args) throws Exception {
        int arraySizeMax = 1_000_000;
        int arraySizeInit = 10;
        long begin;

        long summ1 = 0;
        try(MyArrayInt myArr = new MyArrayInt(arraySizeInit)) { // помещение в try для того, чтобы был close (т.к. autocloseable)
            begin = System.currentTimeMillis();

            for (int idx = 0; idx < arraySizeMax; idx++) {
                myArr.setValue(idx, idx);
            }

            for (int idx = 0; idx < arraySizeMax; idx++) {
                summ1 = myArr.getValue(idx);
            }

            System.out.println("myArr time: " + (System.currentTimeMillis() - begin));
        }

        System.out.println("------------------");
        long summ2 = 0;
        List<Integer> arrayList = new ArrayList<>(arraySizeInit);
        begin = System.currentTimeMillis();

        for (int idx = 0; idx < arraySizeMax; idx++) {
            arrayList.add(idx, idx);
        }

        for (int idx = 0; idx < arraySizeMax; idx++) {
            summ2 = arrayList.get(idx);
        }

        System.out.println("ArrayList time: " + (System.currentTimeMillis() - begin));

        System.out.println("summ1: " + summ1 + ", summ2: " + summ2);
    }
}
