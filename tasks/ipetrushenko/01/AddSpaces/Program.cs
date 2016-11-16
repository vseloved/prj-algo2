using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication4
{
    class Program
    {
        static void Main(string[] args)
        {
            HashSet<string> dict = ReadDictionary();
            Console.WriteLine(bestSplit(dict, "thisiscat"));
        }

        public static string bestSplit(HashSet<string> dictionary, string sentence)
        {
            ParseResult[] memo = new ParseResult[sentence.Length];
            ParseResult r = split(dictionary, sentence, 0, memo);
            return r == null ? null : r.Parsed;
        }

        public static ParseResult split(HashSet<string> dictionary, string sentence, int start, ParseResult[] memo)
        {
            if (start >= sentence.Length)
            {
                return new ParseResult(0, "");
            }

            if (memo[start] != null)
            {
                return memo[start];
            }

            int bestInvalid = int.MaxValue;
            string bestParsing = null;
            string partial = "";

            int index = start;
            while (index < sentence.Length)
            {
                char c = sentence[index];
                partial += c;

                int invalid = dictionary.Contains(partial) ? 0 : partial.Length;
                if (invalid < bestInvalid)
                {
                    var result = split(dictionary, sentence, index + 1, memo);

                    if (invalid + result.Invalid < bestInvalid)
                    {
                        bestInvalid = invalid + result.Invalid;
                        bestParsing = partial + " " + result.Parsed;
                        if (bestInvalid == 0)
                        {
                            break;
                        }
                    }
                }

                index++;
            }

            memo[start] = new ParseResult(bestInvalid, bestParsing);
            return memo[start];
        }

        public class ParseResult
        {
            public int Invalid;
            public string Parsed;

            public ParseResult(int inv, string p)
            {
                Invalid = inv;
                Parsed = p;
            }
        }

        public static HashSet<string> ReadDictionary()
        {
            const string filePath = @"D:\work\prj-algo2\dict_en.txt";
            HashSet<string> dict = new HashSet<string>();

            foreach (var word in File.ReadAllLines(filePath))
            {
                dict.Add(word);
            }

            return dict;
        }
    }
}
