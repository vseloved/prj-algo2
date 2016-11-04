using System.Collections.Generic;
using Graph.Representation;

namespace Graph
{
    public class DepthFirstSearch
    {
        private readonly bool[] _visited;
        private readonly int[] _edgeTo;
        private readonly int _source;

        public DepthFirstSearch(GraphList g, int source)
        {
            _visited = new bool[g.V()];
            _edgeTo = new int[g.V()];
            _source = source;

            for (int i = 0; i < _edgeTo.Length; ++i)
            {
                _edgeTo[i] = -1;
            }

            Dfs(g, source);
        }

        private void Dfs(GraphList g, int v)
        {
            _visited[v] = true;

            foreach (var u in g.Adj(v))
            {
                if (!_visited[u])
                {
                    _edgeTo[u] = v;
                    Dfs(g, u);
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
