
using System.Collections.Generic;
using Graph.Representation;

namespace Graph
{
    interface IShortestPath
    {
        bool HasPathTo(int v);

        IEnumerable<DirectedWeightedEdge> PathTo(int v);

        double DistTo(int v);
    }
}
