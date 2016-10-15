package _03;

public class CoinChangeProblem {

    public int solve(int total, int coins[]) {

        int dp[] = new int[total + 1];
        int p[] = new int[total + 1];

        for (int i = 1; i < total + 1; i++) {
            dp[i] = Integer.MAX_VALUE - 1;
            p[i] = -1;
        }

        for (int i = 0; i < coins.length; i++) {
            for (int j = 1; j <= total; j++) {
                if (j >= coins[i]) {
                    if (dp[j - coins[i]] + 1 < dp[j]) {
                        dp[j] = dp[j - coins[i]] + 1;
                        p[j] = i;
                    }
                }
            }
        }

        if (p[p.length - 1] == -1) {
            System.out.println("No solution");
            return dp[total];
        }
        int i = p.length - 1;
        System.out.println("Coins:");
        while (i != 0) {
            int j = p[i];
            System.out.print(coins[j] + " ");
            i = i - coins[j];
        }
        System.out.println();
        return dp[total];
    }

    public static void main(String[] args) {
        CoinChangeProblem app = new CoinChangeProblem();
        System.out.println("Total:" + app.solve(100, new int [] {1, 3, 7, 13, 29, 50}));
    }
}
