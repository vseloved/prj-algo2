package lesson4.longest_common_subsequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dmitin on 01.10.16.
 */
public class App {
    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println(lcs("fyord", "worlfd"));
    }

    private List<Pair<Character>> lcs(String string1, String string2) {
        int size1 = string1.length();
        int size2 = string2.length();
        int[][] x = new int[size1 + 1][size2 + 1];
        for (int i = 0; i <= size1; i++) {
            for (int j = 0; j <= size2; j++) {
                if (i == 0 || j == 0) {
                    x[i][j] = 0;
                } else if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    x[i][j] = x[i - 1][j - 1] + 1;
                } else {
                    x[i][j] = Math.max(x[i - 1][j], x[i][j - 1]);
                }
            }
        }

        for (int i = 0; i <= size1; i++) {
            System.out.println(Arrays.toString(x[i]));
        }

        List<Pair<Character>> res = new ArrayList<>();
        int i = size1;
        int j = size2;
        while (i >= 1 && j >= 1) {
            if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                res.add(0, new Pair<>(string1.charAt(i - 1), string2.charAt(j - 1)));
                i--;
                j--;
            } else if (x[i - 1][j] > x[i][j - 1]) {
                res.add(0, new Pair<>(string1.charAt(i - 1), '_'));
                i--;
            } else {
                res.add(0, new Pair<>('_', string2.charAt(j - 1)));
                j--;
            }
        }
        if (i == 0) {
            for (int k = j; k >= 1; k--) {
                res.add(0, new Pair<>('_', string2.charAt(k - 1)));
            }
        } else {
            for (int k = i; k >= 1; k--) {
                res.add(0, new Pair<>(string1.charAt(k - 1), '_'));
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
