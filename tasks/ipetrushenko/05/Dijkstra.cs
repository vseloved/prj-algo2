using System.Collections.Generic;
using Graph.Representation;

namespace Graph
{
    public class Dijkstra : IShortestPath
    {
        private readonly double[] distTo;
        private readonly DirectedWeightedEdge[] edgeTo;
        private readonly MinPQ<double> pq; 

        public Dijkstra(EdgeWeightedDigraph graph, int source)
        {
            pq = new MinPQ<double>(graph.V());
            edgeTo = new DirectedWeightedEdge[graph.V()];
            distTo = new double[graph.V()];

            for (int i = 0; i < graph.V(); ++i)
            {
                distTo[i] = double.MaxValue;
            }
            distTo[source] = 0.0;

            dijkstra(graph, 0);
        }

        // Relax(u, v, w):
        // if(d[v] > d[u] + w(u, v))
        //    d[v] = d[u] + w(u, v)

        private void dijkstra(EdgeWeightedDigraph graph, int vertex)
        {
            pq.Insert(vertex, 0.0);

            while (!pq.IsEmpty())
            {
                int v = pq.DelMin();
                foreach (DirectedWeightedEdge edge in graph.Adj(v))
                {
                    Relax(edge);
                }
            }
        }

        private void Relax(DirectedWeightedEdge edge)
        {
            var u = edge.To();
            var v = edge.From();

            if (distTo[u] > distTo[v] + edge.Weight())
            {
                distTo[u] = distTo[v] + edge.Weight();
                edgeTo[u] = edge;
            
                if (pq.Contains(u)) { pq.DecreaseKey(u, distTo[u]); }
                else                { pq.Insert(u, distTo[u]); }
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
