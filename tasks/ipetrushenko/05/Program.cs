using System;

namespace Graph
{
    class Program
    {
        static void Main(string[] args)
        {
            const string filePath = @"C:\Users\1\Desktop\tinyG.txt";
            Graph_List g = new Graph_List(filePath);

            DisplayGraph(g);
            Console.WriteLine();

            dfs(g, 0);
            Console.WriteLine();
            bfs(g, 0);
        }

        public static void DisplayGraph(Graph_List g)
        {
            Console.WriteLine("{0} verticies, {1} edges", g.V(), g.E());

            for (int i = 0; i < g.V(); ++i)
            {
                Console.Write("{0}: ", i);
                foreach (var u in g.Adj(i))
                {
                    Console.Write("{0} ", u);
                }
                Console.WriteLine();
            }
        }

        public static void dfs(Graph_List g, int vertex)
        {
            DepthFirstSearch search = new DepthFirstSearch(g, vertex);
            for (int v = 0; v < g.V(); v++)
            {
                Console.Write(vertex + " to " + v + ": ");
                if (search.hasPathTo(v))
                {
                    foreach (int x in search.pathTo(v))
                    {
                        if (x == vertex) { Console.Write(x); }
                        else             { Console.Write("-" + x); }
                    }
                }
                else
                {
                    Console.Write("No path");
                }

                Console.WriteLine();
            } 
        }

        public static void bfs(Graph_List g, int vertex)
        {
            BreadthFirstSearch search = new BreadthFirstSearch(g, vertex);
            for (int v = 0; v < g.V(); v++)
            {
                Console.Write(vertex + " to " + v + ": ");
                if (search.hasPathTo(v))
                {
                    foreach (int x in search.pathTo(v))
                    {
                        if (x == vertex) { Console.Write(x); }
                        else { Console.Write("-" + x); }
                    }
                }
                else
                {
                    Console.Write("No path");
                }

                Console.WriteLine();
            }
        }
    }
}
