package _01;

import java.util.*;

import static java.util.Collections.singletonList;

public class WordBreak {

    public List<String> solve(String s, Set<String> dict) {
        Map<Integer, List<String>> dp = new HashMap<>();
        int max = dict.stream().mapToInt(String::length).max().orElseThrow(RuntimeException::new);
        return subTask(s, dict, 0, max, dp);
    }

    private List<String> subTask(String s, Set<String> dict, int start, int max, Map<Integer, List<String>> dp) {
        if (start == s.length())
            return singletonList("");
        if (dp.containsKey(start))
            return dp.get(start);
        List<String> ans = new ArrayList<>();
        for (int i = start; i < start + max && i < s.length(); i++) {
            String n = s.substring(start, i + 1);
            if (!dict.contains(n)) {
                continue;
            }
            List<String> partial = subTask(s, dict, i + 1, max, dp);
            for (String x : partial)
                ans.add(n + (x.isEmpty() ? "" : " ") + x);
        }
        dp.put(start, ans);
        return ans;
    }

    public static void main(String[] args) {
        Set<String> dict = new HashSet<>();
        dict.add("this");
        dict.add("is");
        dict.add("a");
        dict.add("test");
        dict.add("th");
        dict.add("is");
        WordBreak app = new WordBreak();
        System.out.println(app.solve("thisisatest", dict));
    }
}
