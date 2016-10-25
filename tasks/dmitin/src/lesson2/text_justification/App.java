package lesson2.text_justification;

/**
 * Created by dmitin on 24.09.16.
 */
public class App {

    private static final int LINE_LENGTH = 80;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        String[] words = ("Постановка: необходимо написать функцию, которая получает на вход текст (text) " +
                " и максимальную ширину строки (’liimit`) и разбивает текст так, чтобы он выглядел наиболее эстетично." +
                " Мера эстетичности отдельной строки: (limit - length)^3, если длина строки length <= limit.")
                .split(" ");

        int[] starts = split(words);
//        System.out.println("starts: " + Arrays.toString(starts));
//        System.out.println();
        int start = 0;
        while (start < words.length) {
            int newStart = starts[start];
            int spaceCount = LINE_LENGTH - length(start, newStart, words);
            int wordCount = newStart - start;
            int averageSpaceCount = spaceCount / wordCount;
            int rest = spaceCount % wordCount;
            for (int i = start; i < newStart; i++) {
                System.out.print(words[i]);
                printSpaces(averageSpaceCount + (i < start + rest / 2 || i >= newStart - rest + rest / 2 ? 1 : 0));
            }
            System.out.println();
            start = newStart;
        }
    }

    private void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private int[] split(String[] words) {
        int[] starts = new int[words.length];
        int[] dp = new int[words.length + 1];
        for (int i = words.length - 1; i >= 0; i--) {
            int min = Integer.MAX_VALUE;
            for (int j = i + 1; j <= words.length; j++) {
                int tmp = sumOrInfinity(dp[j], lineBadness(i, j, words));
                if (tmp < min) {
                    min = tmp;
                    starts[i] = j;
                }
            }
            dp[i] = min;
        }
//        System.out.println("DP: " + Arrays.toString(dp));
        return starts;
    }

    private int sumOrInfinity(int x, int y) {
        if (x < Integer.MAX_VALUE / 2 && y < Integer.MAX_VALUE / 2) {
            return x + y;
        }

        return Integer.MAX_VALUE;
    }

    private int lineBadness(int from, int to, String[] words) {
        int length = length(from, to, words);

        if (length + to - from - 1 > LINE_LENGTH) {
            return Integer.MAX_VALUE;
        }

        return (int) Math.pow(LINE_LENGTH - length, 3);
    }

    private int length(int from, int to, String[] words) {
        int length = 0;
        for (int i = from; i < to; i++) {
            length += words[i].length();
        }
        return length;
    }

}
