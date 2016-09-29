package lesson1.string_split;

import java.util.*;

/**
 * Created by dmitin on 29.09.16.
 */
public class App {
//    private int iter;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        Set<String> dict = new HashSet<>(Arrays.asList("this is a test".split(" ")));
        System.out.println(split("thisisatest", dict, new HashMap<>()));
    }

    private List<String> split(String str, Set<String> dict, Map<String, List<String>> cache) {
//        iter++;
//        System.out.println("split: " + str + ", iter: " + iter);
        if (dict.contains(str)) {
            return new ArrayList<>(Collections.singletonList(str));
        }
        for (int i = 1; i <= str.length(); i++) {
            String left = str.substring(0, i);
            String right = str.substring(i);
            List<String> list;
            if (cache.containsKey(right)) {
                list = cache.get(right);
            } else {
                list = split(right, dict, cache);
            }
            if (dict.contains(left) && list != null) {
                list.add(0, left);
                cache.put(str, list);
                return list;
            }
        }
        return null;
    }
}
