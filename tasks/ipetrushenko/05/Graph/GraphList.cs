using System;
using System.Collections.Generic;
using System.IO;

namespace Graph.Graph
{
    public class GraphList : IGraph
    {
        private int _numberOfVerticies;
        private int _numberOfEdges;
        private List<LinkedList<int>> adjList;

        // Create an empty graph with V vertices
        public GraphList(int numberOfVerticies)
        {
            IntializeGraph(numberOfVerticies);
        }

        // Create a graph From file
        public GraphList(string filePath)
        {
            try
            {
                using (StreamReader streamReader = new StreamReader(filePath))
                {
                    IntializeGraph(Convert.ToInt32(streamReader.ReadLine()));
                    int E = Convert.ToInt32(streamReader.ReadLine());

                    for(int i = 0; i < E; ++i)
                    {
                        var splittedLine = streamReader.ReadLine().Split(' ');

                        var u = Convert.ToInt32(splittedLine[0]);
                        var v = Convert.ToInt32(splittedLine[1]);
                        AddEdge(u, v);
                    }
                }
            }
            catch (Exception ex)  
            {
                throw new InvalidOperationException(ex.Message);
            }
        }

        public void AddEdge(int u, int v)
        {
            adjList[u].AddFirst(v);
            adjList[v].AddFirst(u);

            _numberOfEdges++;
        }

        public IEnumerable<int> Adj(int u)
        {
            return adjList[u];
        }

        public int V()
        {
            return _numberOfVerticies;
        }

        public int E()
        {
            return _numberOfEdges;
        }

        private void IntializeGraph(int numberOfVerticies)
        {
            _numberOfVerticies = numberOfVerticies;
            _numberOfEdges = 0;

            adjList = new List<LinkedList<int>>(numberOfVerticies);
            for (int i = 0; i < numberOfVerticies; ++i)
            {
                adjList.Add(new LinkedList<int>());
            }
        }
    }
}
