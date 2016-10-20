package lesson5.shortest_path_notweigted;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dmitin on 08.10.16.
 */
public class App {
    public static final int NEIGHBOR = 1;
    public static final int NOT_VISITED = -1;
    public static final int START = -2;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        int[][] adjacencyMatrix = {
                {0, 1, 0, 0, 1, 1, 0, 0, 0, 1},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };

        int from = 0;
        int to = 8;
        int[] parents = breadthFirstSearch(from, to, adjacencyMatrix);
        List<Integer> path = shortestPath(from, to, parents);
        System.out.println(path);
    }

    private List<Integer> shortestPath(int from, int to, int[] parents) {
        List<Integer> path = new LinkedList<>();
        int vertex = to;
        while (vertex != from) {
            path.add(0, vertex);
            vertex = parents[vertex];
        }
        path.add(0, from);
        return path;
    }

    private int[] breadthFirstSearch(int from, int to, int[][] adjacencyMatrix) {
        int vertexCount = adjacencyMatrix.length;
        int[] parents = new int[vertexCount];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = NOT_VISITED;
        }
        List<Integer> vertexQueue = new LinkedList<>();
        vertexQueue.add(from);
        parents[from] = START;
        while (!vertexQueue.isEmpty()) {
            int vertex = vertexQueue.remove(0);
            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                if (adjacencyMatrix[vertex][neighbor] == NEIGHBOR && parents[neighbor] == NOT_VISITED) {
                    parents[neighbor] = vertex;
                    if (neighbor == to) {
                        return parents;
                    }
                    vertexQueue.add(neighbor);
                }
            }
        }

        throw new IllegalArgumentException(to + " can not be reached from " + from);
    }
}
