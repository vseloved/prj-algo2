using System;
using System.Text;

namespace TextJustification
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(TextJustification("«Я — смерть, великий разрушитель миров, несущий гибель всему живому", 10));
        }

        public static string TextJustification(string text, int pageWidth)
        {
            string[] words = text.Split(' ');
            
            int[] dp = new int[words.Length];
            int[] path = new int[words.Length];
            int numberOfWords = words.Length - 1;

            int[,] badnessTable = CreateBadnessTable(words, pageWidth);
            for (int i = numberOfWords; i >= 0; --i)
            {
                dp[i] = badnessTable[i, numberOfWords];
                path[i] = words.Length;

                // dp[i] = min badness for suffix words[i:]
                // dp[i] = min(badness(i,j) + dp[j] for j in range(i+1, n))
                for (int j = numberOfWords; j > i; --j)
                {
                    int howBadisLine = badnessTable[i, j - 1];

                    if (howBadisLine == int.MaxValue) { continue; }

                    int currentMin = dp[j] + howBadisLine;
                    if (dp[i] > currentMin)
                    {
                        dp[i] = currentMin;
                        path[i] = j;
                    }
                }
            }

            return RestorePath(path, words);
        }

        private static int[,] CreateBadnessTable(string[] words, int pageWidth)
        {
            int[,] table = new int[words.Length, words.Length];
            for (int i = 0; i < words.Length; ++i)
            {
                for (int j = i; j < words.Length; ++j)
                {
                    string line = GetLine(words, i, j);
                    table[i,j] = ComputeBadnessOfLine(line.Length, pageWidth);
                }
            }

            return table;
        }

        private static string GetLine(string[] words, int start, int end)
        {
            StringBuilder sb = new StringBuilder();

            for (int i = start; i <= end; ++i)
            {
                sb.Append(words[i]);
                sb.Append(' ');
            }

            return sb.ToString();
        }

        private static int ComputeBadnessOfLine(int lineWidth, int pageWidth)
        {
            int diff = pageWidth - lineWidth;
            if (diff < 0)
            {
                return int.MaxValue;
            }

            return diff * diff;
        }

        private static string RestorePath(int[] path, string[] words)
        {
            StringBuilder sb = new StringBuilder();

            int start_index = 0; 

            do
            {
                int new_start = path[start_index];
                for (int i = start_index; i < new_start; i++)
                {
                    sb.Append(words[i]);
                    sb.Append(" ");
                }

                sb.Append("\n");
                start_index = new_start;

            } while (start_index < words.Length);
            
            return sb.ToString();
        }
    }
}
