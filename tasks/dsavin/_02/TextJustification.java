package _02;

public class TextJustification {

    public static int badness(String [] words, int i, int j, int w) {
        int length = words[i].length();
        for (int k = i + 1; k <= j; k++)
            length += words[k].length() + 1;
        return length > w ? Integer.MAX_VALUE : (int)Math.pow((w - length), 2);
    }

    public static String justify(String [] words, int w) {
        int len = words.length;
        int[][] costs = new int[len][len];
        for (int i = 0; i < len; i++) {
            costs[i][i] = badness(words, i, i, w);
            for (int j = i + 1; j < len; j++) {
                costs[i][j] = badness(words, i, j, w);
            }
        }
        int min_c [] = new int[len];
        int result[] = new int[len];
        for (int i = len - 1; i>=0; i--) {
            min_c[i] = costs[i][len - 1];
            result[i] = len;
            for (int j = len - 1; j > i; j--) {
                if (costs[i][j - 1] == Integer.MAX_VALUE) continue;
                if (min_c[i] > min_c[j] + costs[i][j - 1]) {
                    min_c[i] = min_c[j] + costs[i][j - 1];
                    result[i] = j;
                }
            }
        }
        StringBuilder justified = new StringBuilder();
        int i = 0;
        while (i < len) {
            int j = i;
            for (; j < result[i]; j++)
                justified.append(words[j]).append(" ");
            justified.append("\n");
            i = j;
        }
        return justified.toString();
    }

    public static void main(String[] args) {
        String text = "Machine learning is a subfield of computer science that evolved from the study of pattern recognition and computational learning theory in artificial intelligence. In 1959, Arthur Samuel defined machine learning as a Field of study that gives computers the ability to learn without being explicitly programmed\". Machine learning explores the study and construction of algorithms that can learn from and make predictions on data. Such algorithms operate by building a model from example inputs in order to make data-driven predictions or decisions, rather than following strictly static program instruction.";
        String justify = justify(text.split("\\s"), 80);
        System.out.println(justify);
    }
}
