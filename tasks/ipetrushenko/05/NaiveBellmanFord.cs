using System.Collections.Generic;

namespace Graph
{
    public class NaiveBellmanFord
    {
        private readonly double[] _distTo;
        private readonly DirectedWeightedEdge[] _edgeTo;

        public NaiveBellmanFord(EdgeWeightedDigraph graph, int source)
        {
            _edgeTo = new DirectedWeightedEdge[graph.V()];
            _distTo = new double[graph.V()];

            for (int i = 0; i < graph.V(); ++i)
            {
                _distTo[i] = double.MaxValue;
            }

            _distTo[source] = 0.0;

            SPBellmanFord(graph);
        }

        private void SPBellmanFord(EdgeWeightedDigraph graph)
        {
            for (int pass = 0; pass < graph.V(); pass++)
            {
                for (int v = 0; v < graph.V(); v++)
                {
                    foreach (var edge in graph.Adj(v))
                    {
                        Relax(edge);
                    }
                }
            }
        }

        private void Relax(DirectedWeightedEdge edge)
        {
            int from = edge.From();
            int to = edge.To();

            if (_distTo[to] > _distTo[from] + edge.Weight())
            {
                _distTo[to] = _distTo[from] + edge.Weight();
                _edgeTo[to] = edge;
            }
        }

        public IEnumerable<DirectedWeightedEdge> PathTo(int v)
        {
            if (!HasPathTo(v)) { return null; }

            var path = new Stack<DirectedWeightedEdge>();
            for (var e = _edgeTo[v]; e != null; e = _edgeTo[e.From()])
            {
                path.Push(e);
            }
            return path;
        }

        public bool HasPathTo(int v)
        {
            return _distTo[v] < double.MaxValue;
        }

        public double DistTo(int v)
        {
            return _distTo[v];
        }
    }
}
