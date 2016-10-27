using System;
using System.Collections.Generic;
using System.IO;

namespace graph
{
    public class Graph_Matrix
    {
        private int _numberOfVerticies;
        private int _numberOfEdges;
        private int[,] adjMatrix;

        // Create an empty graph with V vertices
        public Graph_Matrix(int numberOfVerticies)
        {
            IntializeGraph(numberOfVerticies);
        }

        // Create a graph from file
        public Graph_Matrix(string filePath)
        {
            try
            {
                using (StreamReader streamReader = new StreamReader(filePath))
                {
                    IntializeGraph(Convert.ToInt32(streamReader.ReadLine()));

                    for (int i = 0; i < _numberOfVerticies; ++i)
                    {
                        var splittedLine = streamReader.ReadLine().Split(' ');
                        for (int j = 0; j < _numberOfVerticies; ++j)
                        {
                            if (string.Equals(splittedLine[j], "1"))
                            {
                                AddEdge(i, j);
                            }
                        }
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
            adjMatrix[u, v] = 1;

            _numberOfEdges++;
        }

        public List<int> Adj(int u)
        {
            List<int> result = new List<int>();
            for (int i = 0; i < _numberOfVerticies; ++i)
            {
                result.Add(adjMatrix[u, i]);
            }

            return result;
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

            adjMatrix = new int[numberOfVerticies, numberOfVerticies];
        }
    }
}