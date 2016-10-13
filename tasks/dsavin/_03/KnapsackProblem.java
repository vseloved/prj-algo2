package _03;

public class KnapsackProblem {
    public int solveWithRepetitions(int [] value, int [] weight, int n, int w) {

        int[] dp = new int[w + 1];

        for (int i = 1; i <= w; i++) {
            for (int j = 0; j < n; j++) {
                if (weight[j] <= i ) {
                    if (dp[i - weight[j]] + value[j] > dp[i]) {
                        dp[i] = Math.max(dp[i], dp[i - weight[j]] + value[j]);
                    }
                }
            }
        }
        return dp[w];
    }

    public int solveWithoutRepetitions(int [] value, int [] weight, int n, int w) {

        int[][] dp = new int[n + 1][w + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                dp[i][j] = dp[i - 1][j];
                if (weight[i - 1] <= j) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - weight[i - 1]] + value[i - 1]);
                }
            }
        }

        //Restore solution
        int i = n;
        int j = w;
        System.out.println("Items packed");
        while (i > 0 && j > 0) {
            if (dp[i][j] != dp[i-1][j]) {
                System.out.println("Value : " + value[i - 1] + ", Weight : " + weight[i - 1]);
                j-= weight[i - 1];
            }
            i--;
        }

        return dp[n][w];
    }



    public static void main(String[] args) {
        int W = 10;
        int w[] = {6, 3, 4, 2};
        int c[] = {30, 14, 16, 9};
        final KnapsackProblem app = new KnapsackProblem();
        System.out.println("Val : " + app.solveWithRepetitions(c, w, c.length, W));
        System.out.println("Val : " + app.solveWithoutRepetitions(c, w, c.length, W));
    }
}
