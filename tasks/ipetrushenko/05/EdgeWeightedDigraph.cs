using System;
using System.Collections.Generic;
using System.IO;

namespace Graph
{
    public class EdgeWeightedDigraph
    {
        private int _numberOfVertices;
        private int _numberOfEdges;
        private List<LinkedList<DirectedWeightedEdge>> adjList;

        public EdgeWeightedDigraph(int numberOfVertices)
        {
            InitializeGraph(numberOfVertices, 0);
        }

        public EdgeWeightedDigraph(string fileName)
        {
            try
            {
                using (StreamReader streamReader = new StreamReader(fileName))
                {
                    _numberOfVertices = Convert.ToInt32(streamReader.ReadLine());
                    _numberOfEdges = Convert.ToInt32(streamReader.ReadLine());

                    InitializeGraph(_numberOfVertices, _numberOfEdges);

                    string line;
                    while ((line = streamReader.ReadLine()) != null)
                    {
                        var splittedLine = line.Split(' ');

                        var edge = new DirectedWeightedEdge(Convert.ToInt32(splittedLine[0]), 
                                                            Convert.ToInt32(splittedLine[1]),
                                                            Convert.ToDouble(splittedLine[2]));
                        AddEdge(edge);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new InvalidOperationException(ex.Message);
            }
        }

        public IEnumerable<DirectedWeightedEdge> Adj(int v)
        {
            return adjList[v];
        }

        public void AddEdge(DirectedWeightedEdge edge)
        {
            int u = edge.From();
            adjList[u].AddFirst(edge);
        }

        public int V()
        {
            return _numberOfVertices;
        }

        public int E()
        {
            return _numberOfEdges;
        }

        private void InitializeGraph(int numberOfVertices, int numberOfEdges)
        {
            _numberOfVertices = numberOfVertices;
            _numberOfEdges = numberOfEdges;

            adjList = new List<LinkedList<DirectedWeightedEdge>>(numberOfVertices);

            for (int i = 0; i < numberOfVertices; ++i)
            {
                adjList.Add(new LinkedList<DirectedWeightedEdge>());
            }
        }
    }
}
