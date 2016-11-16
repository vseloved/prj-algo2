using System.Collections.Generic;
using Graph.Representation;

namespace Graph
{
    public class BreadthFirstSearch
    {
        private readonly bool[] _visited;
        private readonly int[] _edgeTo;
        private readonly int _source;

        public BreadthFirstSearch(GraphList g, int source)
        {
            _visited = new bool[g.V()];
            _edgeTo = new int[g.V()];
            _source = source;

            for (int i = 0; i < _edgeTo.Length; ++i)
            {
                _edgeTo[i] = -1;
            }

            Bfs(g, source);
        }

        private void Bfs(GraphList g, int vertex)
        {
            Queue<int> queue = new Queue<int>();
            queue.Enqueue(vertex);
            _visited[vertex] = true;

            while (queue.Count != 0)
            {
                int v = queue.Dequeue();
                foreach (var u in g.Adj(v))
                {
                    if (!_visited[u])
                    {
                        queue.Enqueue(u);
                        _visited[u] = true;
                        _edgeTo[u] = v;
                    }
                }
            }
        }

        public bool HasPathTo(int v)
        {
            return _visited[v];
        }

        public Stack<int> PathTo(int v)
        {
            if (!HasPathTo(v)) { return null; }

            Stack<int> path = new Stack<int>();
            for (int x = v; x != _source; x = _edgeTo[x])
            {
                path.Push(x);
            }

            path.Push(_source);
            return path;
        }
    }
}
