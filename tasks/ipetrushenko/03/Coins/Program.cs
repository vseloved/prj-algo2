using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication3
{
    class Program
    {
        static void Main(string[] args)
        {
            IList<int> coins = new List<int> { 1, 3, 7, 13, 29, 50 };
            int n = 10;
            Console.WriteLine(CoinChange(n, coins)); // output = 6
        }

        private static int CoinChange(int n, IList<int> coins)
        {
            int[,] dp = new int[n + 1, coins.Count];

            // If n is exactly 0, we should count that as 1 way to make change.
            for (int i = 0; i < coins.Count; i++)
            {
                dp[0, i] = 1;
            }

            // The number of ways to change amount a using m kinds of coins equals
            // the number of ways to change amount a using all but the first kind of coin, plus
            // the number of ways to change amount a - d using all kinds of coins, where d is the denomination of the first kind of coin
            for (int i = 1; i < n+1; i++)
            {
                for (int j = 0; j < coins.Count; j++)
                {
                    // Count of solutions including coins[j]
                    var x = (i - coins[j] >= 0)? dp[i - coins[j], j]: 0;
 
                    // Count of solutions excluding coins[j]
                    var y = (j >= 1)? dp[i, j-1]: 0;
 
                    // total count
                    dp[i, j] = x + y;
                }
            }

            return dp[n, coins.Count-1];
        }
    }
}
