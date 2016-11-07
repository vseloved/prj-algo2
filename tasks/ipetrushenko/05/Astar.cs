using System;
using System.Collections.Generic;
using Graph.Representation;

namespace Graph
{
    public class Astar : IShortestPath
    {
        private readonly double[] distTo;
        private readonly DirectedWeightedEdge[] edgeTo;
        private readonly MinPQ<double> pq;

        public Astar(EdgeWeightedDigraph graph, int source, int target)
        {
            pq = new MinPQ<double>(graph.V());
            edgeTo = new DirectedWeightedEdge[graph.V()];
            distTo = new double[graph.V()];

            for (int i = 0; i < graph.V(); ++i)
            {
                distTo[i] = double.MaxValue;
            }
            distTo[source] = 0.0;

            AstarSP(graph, 0, target);
        }

        private void AstarSP(EdgeWeightedDigraph graph, int vertex, int target)
        {
            pq.Insert(vertex, 0.0);

            while (!pq.IsEmpty())
            {
                int v = pq.DelMin();

                if (v == target) { break; }

                foreach (DirectedWeightedEdge edge in graph.Adj(v))
                {
                    Relax(edge, target);
                }
            }
        }

        public bool HasPathTo(int v)
        {
            return distTo[v] < double.MaxValue;
        }

        public double DistTo(int v)
        {
            return distTo[v];
        }

        private void Relax(DirectedWeightedEdge edge, int target)
        {
            var u = edge.To();
            var v = edge.From();

            if (distTo[u] > distTo[v] + edge.Weight())
            {
                distTo[u] = distTo[v] + edge.Weight();
                edgeTo[u] = edge;

                if (pq.Contains(u)) { pq.DecreaseKey(u, heuristic(target, u) + distTo[u]); }
                else                { pq.Insert(u,      heuristic(target, u) + distTo[u]); }
            }
        }

        private static int heuristic(int target, int current)
        {
            //# Manhattan distance on a square grid
            //return Math.Abs(target.x - current.x) + Math.Abs(target.y - current.y);
            return 0;
        }
        
        public IEnumerable<DirectedWeightedEdge> PathTo(int v)
        {
            if (!HasPathTo(v)) { return null; }

            var path = new Stack<DirectedWeightedEdge>();
            for (var e = edgeTo[v]; e != null; e = edgeTo[e.From()])
            {
                path.Push(e);
            }
            return path;
        }

    }
}
