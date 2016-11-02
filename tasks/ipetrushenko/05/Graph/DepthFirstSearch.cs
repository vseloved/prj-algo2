using System.Collections.Generic;

namespace Graph.Graph
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

            dfs(g, source);
        }

        private void dfs(GraphList g, int v)
        {
            _visited[v] = true;

            foreach (var u in g.Adj(v))
            {
                if (!_visited[u])
                {
                    _edgeTo[u] = v;
                    dfs(g, u);
                }
            }     
        }

        public bool hasPathTo(int v)
        {
            return _visited[v];
        }

        public Stack<int> pathTo(int v)
        {
            if (!hasPathTo(v)) { return null; }

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
