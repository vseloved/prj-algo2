package lesson3.shortest_path;

import java.util.*;
import java.util.stream.Stream;


/**
 * Created by dmitin on 02.10.16.
 */
public class App {

    private static final int WHITE = 0;
    private static final int GREY = 1;
    private static final int BLACK = 2;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        NeighborAndWeight[][] adjacencyList = new NeighborAndWeight[][]{
                new NeighborAndWeight[]{new NeighborAndWeight(4, 2)},
                new NeighborAndWeight[]{
                        new NeighborAndWeight(2, 1),
                        new NeighborAndWeight(5, 3),
                        new NeighborAndWeight(4, 4),
                        new NeighborAndWeight(0, 1)
                },
                new NeighborAndWeight[]{new NeighborAndWeight(5, 1)},
                new NeighborAndWeight[0],
                new NeighborAndWeight[]{
                        new NeighborAndWeight(3, 3),
                        new NeighborAndWeight(7, 1)
                },
                new NeighborAndWeight[]{
                        new NeighborAndWeight(6, 2),
                        new NeighborAndWeight(3, 5)
                },
                new NeighborAndWeight[]{new NeighborAndWeight(3, 2)},
                new NeighborAndWeight[]{new NeighborAndWeight(3, 1)}
        };
        int from = 1;
        int to = 3;
        System.out.println(shortestPath(from, to, adjacencyList));
    }

    private List<Integer> shortestPath(int from, int to, NeighborAndWeight[][] adjacencyList) {
        List<Integer> sortedVertices = topologicSort(from, adjacencyList);
        int vertexCount = adjacencyList.length;
        int[] dynamics = new int[vertexCount]; // min weight of path ending in vertex
        int[] minimizingVertices = new int[vertexCount];
        for (int i = 0; i < dynamics.length; i++) {
            dynamics[i] = Integer.MAX_VALUE;
            minimizingVertices[i] = -1;
        }
        dynamics[from] = 0;

        for (int vertex : sortedVertices) {
            for (NeighborAndWeight neighborAndWeight : adjacencyList[vertex]) {
                int neighbor = neighborAndWeight.neighbor;
                int tmp = dynamics[vertex] == Integer.MAX_VALUE ? Integer.MAX_VALUE
                        : dynamics[vertex] + neighborAndWeight.weight;
                if (tmp < dynamics[neighbor]) {
                    dynamics[neighbor] = tmp;
                    minimizingVertices[neighbor] = vertex;
                }
            }
        }

        List<Integer> shortestPath = new LinkedList<>();
        int vertex = to;
        while (vertex != -1) {
            shortestPath.add(0, vertex);
            vertex = minimizingVertices[vertex];
        }
        return shortestPath;
    }

    private List<Integer> topologicSort(int from, NeighborAndWeight[][] adjacencyList) {
        return depthFirstSearch(from, adjacencyList, new int[adjacencyList.length], new ArrayList<>());
    }

    private List<Integer> depthFirstSearch(int from, NeighborAndWeight[][] adjacencyList, int[] vertexColors,
                                           List<Integer> sortedVertices) {
        vertexColors[from] = GREY;
        Stream.of(adjacencyList[from])
                .map(NeighborAndWeight::getNeighbor)
                .forEach(neighbor -> {
                    if (vertexColors[neighbor] == WHITE) {
                        depthFirstSearch(neighbor, adjacencyList, vertexColors, sortedVertices);
                    } else if (vertexColors[neighbor] == GREY) {
                        throw new IllegalArgumentException("Graph has cycle");
                    }
                });
        sortedVertices.add(0, from);
        vertexColors[from] = BLACK;
        return sortedVertices;
    }

    private class NeighborAndWeight {
        private int neighbor;
        private int weight;

        public int getNeighbor() {
            return neighbor;
        }

        public int getWeight() {
            return weight;
        }

        public NeighborAndWeight(int neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }
}
