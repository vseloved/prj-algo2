using System;
using System.Collections.Generic;

namespace ConsoleApplication4
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(LongestCommonSubsequenceLength("MZJAWXU", "XMJYAUZ"));

            MinimumEditDistanceAndAligment("execution", "intention");
            MinimumEditDistanceAndAligment("fyord", "world");
        }

        // d[i,j] = d[i-1, j-1] + 1           x[i] == x[j]
        //          max(d[i, j-1], d[i-1, j]) x[i] != x[j]
        public static int LongestCommonSubsequenceLength(string s, string t)
        {
            int[,] dp = new int[s.Length + 1, t.Length + 1];

            for (int i = 1; i <= s.Length; ++i)
            {
                for (int j = 1; j <= t.Length; ++j)
                {
                    if (s[i - 1] == t[j - 1])
                    {
                        dp[i, j] = dp[i - 1, j - 1] + 1;
                    }
                    else
                    {
                        dp[i, j] = Math.Max(dp[i, j - 1], dp[i - 1, j]);
                    }
                }
            }

            
            return dp[s.Length, t.Length];
        }


        //insertion
        //deletion
        //substitution
        //                _                      
        //              | dp[i-1, j]   + 1
        // dp[i,j] = min| dp[i, j-1]   + 1  
        //              |                   _
        //              |_dp[i-1, j-1] + 1 | if (x[i] != x[j])
        //                             + 0 |_if (x[i] == x[j])
        public static void MinimumEditDistanceAndAligment(string s, string t)
        {
            int[,] dp = new int[s.Length + 1, t.Length + 1];

            for (int i = 0; i <= s.Length; ++i)
            {
                for (int j = 0; j <= t.Length; ++j)
                {
                    if (i == 0)
                    {
                        dp[i, j] = j;
                        continue;
                    }
                    if (j == 0)
                    {
                        dp[i, j] = i;
                        continue;
                    }

                    if (s[i - 1] == t[j - 1])
                    {
                        dp[i, j] = dp[i - 1, j - 1];
                    }
                    else
                    {
                        //s      t
                        dp[i, j] = 1 + min(dp[i,     j - 1],  // deletion t
                                           dp[i - 1,     j],  // deletion s 
                                           dp[i - 1, j - 1]); // exchange
                    }
                }
            }

            int x = s.Length;
            int y = t.Length;

            Stack<char> sb1 = new Stack<char>(); 
            Stack<char> sb2 = new Stack<char>();

            while (x > 0 && y > 0)
            {
                if (x == 0 && y == 0)
                {
                    break;
                }

                if (s[x - 1] == t[y - 1])
                {
                    sb1.Push(s[x - 1]);
                    sb2.Push(t[y - 1]);
                    x = x - 1;
                    y = y - 1;
                }
                else if (dp[x, y] == dp[x, y - 1] + 1)
                {
                    sb1.Push(s[x - 1]);
                    sb2.Push(t[y - 1]);
                    y = y - 1;
                }
                else if (dp[x, y] == dp[x - 1, y] + 1)
                {
                    sb2.Push('_');
                    x = x - 1;
                    sb1.Push(s[y - 1]);
                }
                else if (dp[x, y] == dp[x - 1, y - 1] + 1)
                {
                    sb1.Push('_');
                    sb2.Push(t[y - 1]);
                    x = x - 1;
                    y = y - 1;
                }
            }

            while (sb1.Count != 0)
            {
                Console.Write(sb1.Pop());
                Console.Write(" ");
            }

            Console.WriteLine();

            while (sb2.Count != 0)
            {
                Console.Write(sb2.Pop());
                Console.Write(" ");
            }

            Console.WriteLine();
        }

        private static int min(int x, int y, int z)
        {
            return Math.Min(Math.Min(x, y), z);
        }
    }
}
