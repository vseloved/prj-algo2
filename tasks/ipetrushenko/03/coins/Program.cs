using System;
using System.Collections.Generic;

namespace Coins
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            OptimalChange(new List<int> { 2, 3 }, 1);        // -1
            OptimalChange(new List<int> { 3 }, 3);           // 3
            OptimalChange(new List<int> { 1, 3, 5 }, 11);    // 1 5 5
        }

        // dp[i] = dp[i - coins[j]] + 1
        // If that amount of money cannot be made up by any combination of the coins, return -1.
        public static void OptimalChange(List<int> coins, int n)
        {
            int[] dp = new int[n + 1];
            dp[0] = 0;

            int[] output = new int[n + 1];
            for (int i = 1; i < n + 1; ++i)
            {
                dp[i] = n + 1;
                for (int j = 0; j < coins.Count; ++j)
                {
                    if (i >= coins[j] && (dp[i - coins[j]] + 1 < dp[i]))
                    {
                        dp[i] = dp[i - coins[j]] + 1;

                        output[i] = coins[j];
                    }
                }
            }

            if (dp[n] == n + 1) { Console.WriteLine(-1); return; }

            while (n > 0)
            {
                int coin = output[n];
                Console.WriteLine(coin);
                n -= coin;
            }
        }

        public static int CoinsChange(List<int> coins, int n)
        {
            int[,] dp = new int[coins.Count, n + 1];

            for (int hight = 0; hight < coins.Count; ++hight)
            {
                dp[hight, 0] = 1;
            }

            // dp[i, j] = dp[i-1, j] + dp[i, j - coins[i]]
            for (int i = 0; i < coins.Count; ++i)
            {
                for (int j = 1; j < n + 1; ++j)
                {
                    int x = i >= 1 ? dp[i - 1, j] : 0;
                    int y = (j >= coins[i]) ? dp[i, j - coins[i]] : 0;

                    dp[i, j] = x + y;
                }
            }

            return dp[coins.Count - 1, n];
        }
    }
}
