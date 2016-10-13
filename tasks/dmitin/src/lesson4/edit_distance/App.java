package lesson4.edit_distance;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dmitin on 13.10.16.
 */
public class App {

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println(editDistance("fyord", "worlfd"));
    }

    private List<Pair<Character>> editDistance(String string1, String string2) {
        int size1 = string1.length();
        int size2 = string2.length();
        int[][] x = new int[size1 + 1][size2 + 1];
        for (int i = 0; i <= size1; i++) {
            for (int j = 0; j <= size2; j++) {
                if (i == 0) {
                    x[i][j] = j;
                } else if (j == 0) {
                    x[i][j] = i;
                } else {
                    int min = Math.min(x[i - 1][j], x[i][j - 1]);
                    if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                        x[i][j] = Math.min(min + 1, x[i - 1][j - 1]);
                    } else {
                        x[i][j] = Math.min(min + 1, x[i - 1][j - 1] + 1);
                    }
                }
            }
        }

//        for (int i = 0; i <= size1; i++) {
//            System.out.println(Arrays.toString(x[i]));
//        }

        System.out.println("edit distance: " + x[size1][size2]);

        List<Pair<Character>> res = new LinkedList<>();
        int i = size1;
        int j = size2;
        while (i >= 1 && j >= 1) {
            int a = x[i - 1][j] + 1;
            int b = x[i][j - 1] + 1;
            int c = x[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 0 : 1);
            int min = Math.min(Math.min(a, b), c);
            if (min == a) {
                res.add(0, new Pair<>(string1.charAt(i - 1), '_'));
                i--;
            } else if (min == b) {
                res.add(0, new Pair<>('_', string2.charAt(j - 1)));
                j--;
            } else {
                res.add(0, new Pair<>(string1.charAt(i - 1), string2.charAt(j - 1)));
                i--;
                j--;
            }
        }

        if (i == 0) {
            while (j >= 1) {
                res.add(0, new Pair<>('_', string2.charAt(j - 1)));
                j--;
            }
        } else {
            while (i >= 1) {
                res.add(0, new Pair<>(string1.charAt(i - 1), '_'));
                i--;
            }
        }
        return res;
    }

    private class Pair<T> {
        private T first;
        private T second;

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" +
                    first +
                    ", " + second +
                    ')';
        }
    }
}
