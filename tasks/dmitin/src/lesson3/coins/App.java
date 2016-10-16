package lesson3.coins;

import java.util.*;

/**
 * Created by dmitin on 29.09.16.
 */
public class App {
    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println(change(1000, new HashSet<>(Arrays.asList(1, 3, 7, 13, 29, 50))));
    }

    private ImmutableList<Integer> change(int sum, Set<Integer> coins) {
        return change(sum, coins, new HashMap<>());
    }

    private ImmutableList<Integer> change(int sum, Set<Integer> coins, Map<Integer, ImmutableList<Integer>> cache) {
        if (sum < 0) {
            return null;
        }

        if (cache.containsKey(sum)) {
            return cache.get(sum);
        }

        ImmutableList<Integer> res;
        if (sum == 0) {
            res = NIL;
        } else if (coins.contains(sum)) {
            res = new Cons<>(sum, NIL);
        } else {
            int minSize = Integer.MAX_VALUE;
            int minimizingCoin = -1;
            ImmutableList<Integer> otherCoins = null;
            for (int coin : coins) {
                ImmutableList<Integer> tmp = change(sum - coin, coins, cache);
                if (tmp != null) {
                    int size = tmp.size();
                    if (size < minSize) {
                        minSize = size;
                        minimizingCoin = coin;
                        otherCoins = tmp;
                    }
                }
            }
            if (otherCoins != null) {
                res = new Cons<>(minimizingCoin, otherCoins);
            } else {
                res = null;
            }

        }

        cache.put(sum, res);
        return res;
    }

    private abstract class ImmutableList<T> {
        abstract public T head();
        abstract public ImmutableList<T> tail();
        abstract public int size();
    }

    private class Cons<T> extends ImmutableList<T> {
        private T head;
        private ImmutableList<T> tail;
        private int size;

        public Cons(T head, ImmutableList<T> tail) {
            this.head = head;
            this.tail = tail;
            this.size = tail.size() + 1;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public ImmutableList<T> tail() {
            return tail;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public String toString() {
            return head.toString() + " : " + tail.toString();
        }
    }

    private class Nil<T> extends ImmutableList<T> {
        @Override
        public T head() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ImmutableList<T> tail() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public String toString() {
            return "nil";
        }
    }

    private final ImmutableList<Integer> NIL = new Nil<>();


}
