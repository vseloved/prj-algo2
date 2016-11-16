using System.Collections.Generic;

namespace Graph.Representation
{
    interface IGraph
    {
        // get adjacent vertices To u
        IEnumerable<int> Adj(int u);

        // add an edge between u and v
        void AddEdge(int u, int v);

        // get number of verticies
        int V();

        // get number of edges
        int E();
    }
}
