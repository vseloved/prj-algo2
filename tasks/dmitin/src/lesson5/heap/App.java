package lesson5.heap;

/**
 * Created by dmitin on 06.10.16.
 */
public class App {
    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        Integer[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Heap<Integer> heap = new Heap<>(arr);
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
        System.out.println(heap.check());

        System.out.println();
        heap.print();
    }

}
