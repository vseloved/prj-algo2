package lesson5.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dmitin on 06.10.16.
 */
public class App {
    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        Integer[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println(checkHeap(Arrays.asList(arr)));
        List<Integer> heap = heapify(arr);
        System.out.println(checkHeap(heap));
        printHeap(heap);
        System.out.println("min: " + extractMin(heap));
        printHeap(heap);
        System.out.println("min: " + extractMin(heap));
    }

    public List<Integer> heapify(Integer[] arr) {
        List<Integer> heap = new ArrayList<>();
        for (int el : arr) {
            add(el, heap);
        }
        return heap;
    }

    public void add(int el, List<Integer> heap) {
        heap.add(el);
        heapUp(heap, heap.size() - 1);
    }

    public int extractMin(List<Integer> heap) {
        int res = heap.get(0);
        Integer last = heap.remove(heap.size() - 1);
        heap.set(0, last);
        heapDown(heap, 0);
        return res;
    }

    public boolean checkHeap(List<Integer> heap) {
        for (int idx = 0; idx < heap.size(); idx++) {
            Integer el = heap.get(idx);
            int leftIdx = hleft(idx);
            if (leftIdx >= heap.size()) {
                return true;
            }
            int leftEl = heap.get(leftIdx);
            int rightIdx = hright(idx);
            if (rightIdx >= heap.size()) {
                return el <= leftEl;
            }
            int rightEl = heap.get(rightIdx);
            if (el > leftEl || el > rightEl) {
                return false;
            }
        }
        return true;
    }

    public void printHeap(List<Integer> heap) {
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

    public void heapUp(List<Integer> heap, int i) {
        int idx = i;
        while (true) {
            int parentIdx = hparent(idx);
            if (parentIdx < 0) {
                break;
            }
            if (heap.get(idx) >= heap.get(parentIdx)) {
                break;
            }
            swap(heap, idx, parentIdx);
            idx = parentIdx;
        }
    }

    public void heapDown(List<Integer> heap, int i) {
        int idx = i;
        while (true) {
            Integer el = heap.get(idx);
            int leftIdx = hleft(idx);
            if (leftIdx >= heap.size()) {
                break;
            }
            int leftEl = heap.get(leftIdx);
            int rightIdx = hright(idx);
            if (rightIdx >= heap.size()) {
                if (el <= leftEl) {
                    break;
                } else {
                    swap(heap, idx, leftIdx);
                    idx = leftIdx;
                }
            }
            int rightEl = heap.get(rightIdx);
            if (el <= leftEl && el <= rightEl) {
                break;
            } else if (leftEl < rightEl) {
                swap(heap, idx, leftIdx);
                idx = leftIdx;
            } else {
                swap(heap, idx, rightIdx);
                idx = rightIdx;
            }
        }
    }

    private void swap(List<Integer> heap, int i, int j) {
        Integer tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }
}
