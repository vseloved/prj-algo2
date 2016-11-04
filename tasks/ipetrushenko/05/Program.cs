using System;
using Graph.Representation;

namespace Graph
{
    class Program
    {
        //Input File:
        // 8
        // 15
        // 4 5 0.35
        // 5 4 0.35
        // 4 7 0.37
        // 5 7 0.28
        // 7 5 0.28
        // 5 1 0.32
        // 0 4 0.38
        // 0 2 0.26
        // 7 3 0.39
        // 1 3 0.29
        // 2 7 0.34
        // 6 2 0.40
        // 3 6 0.52
        // 6 0 0.58
        // 6 4 0.93

        static void Main(string[] args)
        {
            const string filePath = @"C:\Users\1\Desktop\tinyEWD.txt";

            var graph = new EdgeWeightedDigraph(filePath);
            DisplayEWDGraph(graph);

            Console.WriteLine();

            var dj = new Dijkstra(graph, 0);
            PrintShortestPath(dj, graph, 0, graph.V());

            Console.WriteLine();

            var bf = new NaiveBellmanFord(graph, 0);
            PrintShortestPath(bf, graph, 0, graph.V());

            Console.WriteLine();

            var a = new Astar(graph, 0, 3);
            PrintShortestPath(a, graph, 0, 3);
        }

        public static void PrintShortestPath(IShortestPath sp, EdgeWeightedDigraph g, int source, int destination)
        {
            // print shortest path
            for (int t = 0; t < g.V(); t++)
            {
                if (t == destination) { break; }

                if (sp.HasPathTo(t))
                {
                    Console.Write("{0} to {1} is {2}  ", source, t, sp.DistTo(t));
                    foreach (var e in sp.PathTo(t))
                    {
                        Console.Write("[{0}-{1}, {2}] ", e.From(), e.To(), e.Weight());
                    }
                    Console.WriteLine();
                }
                else
                {
                    Console.Write("{0} to {1} no path\n", source, t);
                }
            }
        }


        public static void DisplayGraph(GraphList g)
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

        public static void DisplayEWDGraph(EdgeWeightedDigraph g)
        {
            Console.WriteLine("{0} verticies, {1} edges", g.V(), g.E());

            for (int i = 0; i < g.V(); ++i)
            {
                Console.Write("{0}: ", i);
                foreach (var u in g.Adj(i))
                {
                    Console.Write("[{0}-{1},{2}] ", u.From(), u.To(), u.Weight());
                }
                Console.WriteLine();
            }
        }

        public static void dfs(GraphList g, int vertex)
        {
            DepthFirstSearch search = new DepthFirstSearch(g, vertex);
            for (int v = 0; v < g.V(); v++)
            {
                Console.Write(vertex + " To " + v + ": ");
                if (search.HasPathTo(v))
                {
                    foreach (int x in search.PathTo(v))
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

        public static void bfs(GraphList g, int vertex)
        {
            BreadthFirstSearch search = new BreadthFirstSearch(g, vertex);
            for (int v = 0; v < g.V(); v++)
            {
                Console.Write(vertex + " To " + v + ": ");
                if (search.HasPathTo(v))
                {
                    foreach (int x in search.PathTo(v))
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
