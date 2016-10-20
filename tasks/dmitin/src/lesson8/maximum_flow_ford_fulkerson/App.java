package lesson8.maximum_flow_ford_fulkerson;

import java.util.Arrays;

/**
 * Created by dmitin on 20.10.16.
 */
public class App {

    public static final int NOT_VISITED = 0;
    public static final int VISITED = 1;
    public static final int NOT_NEIGHBOR = 0;
    public static final int NO_PARENT = -1;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        int source = 0;
        int sink = 5;
        int[][] adjacencyMatrix = {
                {0, 2, 4, 0, 0, 0},
                {-2, 0, 0, 4, 2, 0},
                {-4, 0, 0, 1, 2, 0},
                {0, -4, -1, 0, 0, 3},
                {0, -2, -2, 0, 0, 5},
                {0, 0, 0, -3, -5, 0}
        };
        System.out.println(fordFulkerson(source, sink, adjacencyMatrix));
    }

    private int fordFulkerson(int source, int sink, int[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        int[][] residualGraphAdjMatrix = new int[vertexCount][];
        for (int i = 0; i < vertexCount; i++) {
            residualGraphAdjMatrix[i] = Arrays.copyOf(adjacencyMatrix[i], adjacencyMatrix[i].length);
        }

        int[] parents = new int[vertexCount];
        parents[source] = NO_PARENT;
        int flow = 0;

        while (depthFirstSearch(source, sink, residualGraphAdjMatrix, parents, new int[vertexCount])) {
            int min = Integer.MAX_VALUE;
            int vertex = sink;
            while (vertex != source) {
                int parent = parents[vertex];
                min = Math.min(min, residualGraphAdjMatrix[parent][vertex]);
                vertex = parent;
            }

            vertex = sink;
            while (vertex != source) {
                int parent = parents[vertex];
                residualGraphAdjMatrix[parent][vertex] -= min;
                residualGraphAdjMatrix[vertex][parent] += min;
                vertex = parent;
            }

            flow += min;
        }

        return flow;
    }

    private boolean depthFirstSearch(int vertex, int sink, int[][] residualGraphAdjMatrix, int[] parents,
                                     int[] vertexColors) {
        vertexColors[vertex] = VISITED;
        for (int neighbor = 0; neighbor < residualGraphAdjMatrix.length; neighbor++) {
            if (residualGraphAdjMatrix[vertex][neighbor] != NOT_NEIGHBOR && vertexColors[neighbor] == NOT_VISITED) {
                parents[neighbor] = vertex;
                depthFirstSearch(neighbor, sink, residualGraphAdjMatrix, parents, vertexColors);
            }
        }
        return vertexColors[sink] == VISITED;
    }
}
