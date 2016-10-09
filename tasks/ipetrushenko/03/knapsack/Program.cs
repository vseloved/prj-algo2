using System;
using System.Collections.Generic;

namespace knapsack
{
    class MainClass
    {
        static void Main(String[] args)
        {
            string[] input = Console.ReadLine().Split(' ');

            //On the first line you are given N and K(number of items). 
            //K lines follow with two integers on each line describing one of your items. 
            //The first number is the size of the item and the next is the value of the item.

            int N = Convert.ToInt32(input[0]);
            int numberOfItems = Convert.ToInt32(input[1]);

            List<Tuple<int, int>> items = new List<Tuple<int, int>>();
            for (int i = 0; i < numberOfItems; ++i)
            {
                string[] item = Console.ReadLine().Split(' ');
                items.Add(new Tuple<int, int>(Convert.ToInt32(item[0]), Convert.ToInt32(item[1])));
            }

            //Simple Input:
            //4 5

            //1 8
            //2 4 => 13 The items with value 8 and 5 should be picked, summing upto 13.
            //3 0
            //2 5
            //2 3

            //Simple Input:
            //15 3

            //12 10
            //7   8   => 13 The items with value 8 and 7 should be picked, summing upto 13.
            //6   7
            knapsack(items, N);
        }

        public static void knapsack(List<Tuple<int, int>> items, int N)
        {
            int[,] dp = new int[items.Count + 1, N + 1];

            for (int i = 0; i < items.Count + 1; ++i)
            {
                for (int j = 0; j < N + 1; ++j)
                {
                    if (i == 0 || j == 0) { dp[i, j] = 0; continue; }

                    if (j - items[i - 1].Item1 >= 0)
                    {
                        dp[i, j] = Math.Max(dp[i - 1, j], dp[i - 1, j - items[i - 1].Item1] + items[i - 1].Item2);

                    }
                    else
                    {
                        dp[i, j] = dp[i - 1, j];
                    }
                }
            }

            printResult(dp, items, items.Count, N);
        }

        public static void printResult(int[,] dp, List<Tuple<int, int>> items, int k, int s)
        {
            if (dp[k, s] == 0) { return; }

            if (dp[k - 1, s] == dp[k, s])
            {
                printResult(dp, items, k - 1, s);
            }
            else
            {
                printResult(dp, items, k - 1, s - items[k - 1].Item1);
                Console.WriteLine(items[k-1].Item2);
            }
        }
    }
}

