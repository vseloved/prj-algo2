package lesson1.string_split;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by dmitin on 29.09.16.
 */
public class App {
    private static final String FILE_NAME = "src/lesson1/string_split/dict_en.txt";
    private static int MAX_WORD_LENGTH;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
//        Set<String> dict = new HashSet<>(Arrays.asList("this", "his", "is", "a", "test", "sat"));
//        MAX_WORD_LENGTH = 4;
        Set<String> dict = init();
        System.out.println(split("thisisatest", dict));
    }

    private Set<String> init() {
        try {
            Set<String> dict = new HashSet<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                dict.add(line);
                int length = line.length();
                if (length > MAX_WORD_LENGTH) {
                    MAX_WORD_LENGTH = length;
                }
            }
            return dict;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ImmutableList<String> split(String str, Set<String> dict) {
        return split(str, dict, new HashMap<>());
    }

    private ImmutableList<String> split(String str, Set<String> dict, Map<String, ImmutableList<String>> cache) {
        if (cache.containsKey(str)) {
            return cache.get(str);
        }

        ImmutableList<String> res = null;
        if (dict.contains(str)) {
            res = new ImmutableList<>(str, null);
        } else {
            int size = Math.min(MAX_WORD_LENGTH, str.length());
            for (int i = 1; i <= size; i++) {
                String left = str.substring(0, i);
                String right = str.substring(i);
                ImmutableList<String> tmp = split(right, dict, cache);

                if (dict.contains(left) && tmp != null) {
                    res = new ImmutableList<>(left, tmp);
                    break;
                }
            }
        }

        cache.put(str, res);
        return res;
    }

    private class ImmutableList<T> {
        private T head;
        private ImmutableList<T> tail;

        public ImmutableList(T head, ImmutableList<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public String toString() {
            return head.toString() + " : " + (tail == null ? "null" : tail.toString());
        }
    }
}
