package lesson5.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitin on 20.10.16.
 */
public class Heap<T extends Comparable<T>> {
    private List<T> heap;

    public Heap(T[] arr) {
        heap = new ArrayList<>();
        for (T el : arr) {
            add(el);
        }
    }

    public void add(T el) {
        heap.add(el);
        heapUp(heap.size() - 1);
    }

    public T extractMin() {
        T res = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        heap.set(0, last);
        heapDown(0);
        return res;
    }

    public boolean check() {
        for (int idx = 0; idx < heap.size(); idx++) {
            T el = heap.get(idx);
            int leftIdx = hleft(idx);
            if (leftIdx >= heap.size()) {
                return true;
            }
            T leftEl = heap.get(leftIdx);
            int rightIdx = hright(idx);
            if (rightIdx >= heap.size()) {
                return el.compareTo(leftEl) <= 0;
            }
            T rightEl = heap.get(rightIdx);
            if (el.compareTo(leftEl) > 0 || el.compareTo(rightEl) > 0) {
                return false;
            }
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < heap.size(); i = hleft(i)) {
            for (int j = i; j < Math.min(hleft(i), heap.size()); j++) {
                System.out.print(heap.get(j));
                int exp = (int) Math.floor(Math.log(heap.size()) / Math.log(2));
                printSpaces((int) Math.pow(2, exp - 1) - i);
            }
            System.out.println();
        }
    }

    private void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private int hparent(int i) {
        return i % 2 == 0 ? i / 2 - 1 : i / 2;
    }

    private int hleft(int i) {
        return 2 * i + 1;
    }

    private int hright(int i) {
        return 2 * i + 2;
    }

    public void heapUp(int i) {
        int idx = i;
        while (true) {
            int parentIdx = hparent(idx);
            if (parentIdx < 0) {
                break;
            }
            if (heap.get(idx).compareTo(heap.get(parentIdx)) >= 0) {
                break;
            }
            swap(idx, parentIdx);
            idx = parentIdx;
        }
    }

    public void heapDown(int i) {
        int idx = i;
        while (true) {
            T el = heap.get(idx);
            int leftIdx = hleft(idx);
            if (leftIdx >= heap.size()) {
                break;
            }
            T leftEl = heap.get(leftIdx);
            int rightIdx = hright(idx);
            if (rightIdx >= heap.size()) {
                if (el.compareTo(leftEl) <= 0) {
                    break;
                } else {
                    swap(idx, leftIdx);
                    idx = leftIdx;
                }
            }
            T rightEl = heap.get(rightIdx);
            if (el.compareTo(leftEl) <= 0 && el.compareTo(rightEl) <= 0) {
                break;
            } else if (leftEl.compareTo(rightEl) < 0) {
                swap(idx, leftIdx);
                idx = leftIdx;
            } else {
                swap(idx, rightIdx);
                idx = rightIdx;
            }
        }
    }

    private void swap(int i, int j) {
        T tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }
}
