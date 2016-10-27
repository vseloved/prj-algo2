package lesson3.knapsack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitin on 02.10.16.
 */
public class App {

    private final Result EMPTY_RESULT = new Result(null, 0);

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        Item item1 = new Item(6, 30);
        Item item2 = new Item(3, 14);
        Item item3 = new Item(4, 16);
        Item item4 = new Item(2, 9);
        ImmutableList<Item> items = new ImmutableList<>(item1, null);
        items = new ImmutableList<>(item2, items);
        items = new ImmutableList<>(item3, items);
        items = new ImmutableList<>(item4, items);
        Result result = knapsack(10, items);
        System.out.println(result.price);
        System.out.println(result.items);
    }

    private Result knapsack(int weight, ImmutableList<Item> items) {
        return knapsack(weight, items, new HashMap<>());
    }

    private Result knapsack(int weight, ImmutableList<Item> items, Map<Input, Result> cache) {
        Input input = new Input(weight, items);
        if (cache.containsKey(input)) {
            return cache.get(input);
        }

        Result result;
        if (weight < 0) {
            result = null;
        } else if (weight == 0 || items == null) {
            result = EMPTY_RESULT;
        } else {
            Item currentItem = items.head;
            ImmutableList<Item> otherItems = items.tail;
            Result withoutCurrentItem = knapsack(weight - currentItem.weight, otherItems, cache);
            Result withCurrentItem = knapsack(weight, otherItems, cache);
            int price;
            ImmutableList<Item> resItems;
            if (withoutCurrentItem != null && withoutCurrentItem.price + currentItem.price >= withCurrentItem.price) {
                price = withoutCurrentItem.price + currentItem.price;
                resItems = new ImmutableList<>(currentItem, withoutCurrentItem.items);
            } else {
                price = withCurrentItem.price;
                resItems = withCurrentItem.items;
            }
            result = new Result(resItems, price);
        }
        cache.put(input, result);
        return result;
    }

    private class Result {
        ImmutableList<Item> items;
        int price;

        public Result(ImmutableList<Item> items, int price) {
            this.items = items;
            this.price = price;
        }
    }

    private class Input {
        int weight;
        ImmutableList<Item> items;

        public Input(int weight, ImmutableList<Item> items) {
            this.weight = weight;
            this.items = items;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Input input = (Input) o;

            if (weight != input.weight) return false;
            return items != null ? items.equals(input.items) : input.items == null;

        }

        @Override
        public int hashCode() {
            int result = weight;
            result = 31 * result + (items != null ? items.hashCode() : 0);
            return result;
        }
    }

    private class Item {
        int weight;
        int price;

        public Item(int weight, int price) {
            this.weight = weight;
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            if (weight != item.weight) return false;
            return price == item.price;

        }

        @Override
        public int hashCode() {
            int result = weight;
            result = 31 * result + price;
            return result;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "weight=" + weight +
                    ", price=" + price +
                    '}';
        }
    }

    private class ImmutableList<T> {
        private T head;
        private ImmutableList<T> tail;

        public ImmutableList(T head, ImmutableList<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ImmutableList<?> that = (ImmutableList<?>) o;

            if (head != null ? !head.equals(that.head) : that.head != null) return false;
            return tail != null ? tail.equals(that.tail) : that.tail == null;

        }

        @Override
        public int hashCode() {
            int result = head != null ? head.hashCode() : 0;
            result = 31 * result + (tail != null ? tail.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return head.toString() + " : " + (tail == null ? "nil" : tail.toString());
        }
    }
}
