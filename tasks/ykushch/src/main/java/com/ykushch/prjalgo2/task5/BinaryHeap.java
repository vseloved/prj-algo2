package com.ykushch.prjalgo2.task5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BinaryHeap {
    private List<Integer> list = new ArrayList<>();

    public int getHeapSize() {
        return list.size();
    }

    public int[] getBinaryHeap() {
        return list.stream()
                .mapToInt(i -> i)
                .toArray();
    }

    public void add(int value) {
        list.add(value);
        int i = getHeapSize() - 1;
        int parent = (i - 1) / 2;

        while (i > 0 && list.get(parent) < list.get(i)) {
            int temp = list.get(i);
            list.set(i, list.get(parent));
            list.set(parent, temp);
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    public void heapify(int i) {
        int heapSize = getHeapSize();
        while (true) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int largestChild = i;

            if(leftChild < heapSize && list.get(leftChild) > list.get(largestChild)) {
                largestChild = leftChild;
            }

            if(rightChild < heapSize && list.get(rightChild) > list.get(largestChild)) {
                largestChild = rightChild;
            }

            if(largestChild == i) {
                break;
            }

            int temp = list.get(i);
            list.set(i, list.get(largestChild));
            list.set(largestChild, temp);
            i = largestChild;
        }
    }

    public void buildHeap(int[] array) {
        list = IntStream.of(array).boxed()
                .collect(toList());
        for(int i = list.size() / 2; i >= 0; i--) {
            heapify(i);
        }
    }

    public Integer getMax() {
        if(list.size() < 1) {
            return null;
        }
        int max = list.get(0);
        list.set(0, list.size() - 1);
        list.remove(list.size() - 1);
        // heapify(0);
        return max;
    }

    public void heapSort(int[] array) {
        buildHeap(array);
        for(int i = array.length - 1; i >= 0; i--) {
            array[i] = getMax();
            heapify(0);
        }
    }


}
