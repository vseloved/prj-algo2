package com.ykushch.prjalgo2.task7;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaxFlow {

    private static final Logger log = LoggerFactory.getLogger(MaxFlow.class);

    public static int findMaxFlow(int[][] capacity, int source, int sink) {
        int flow = 0;
        while(true) {
            int df = findPath(capacity, new boolean[capacity.length], source, sink, Integer.MAX_VALUE);
            if(df == 0) {
                return flow;
            }
            flow += df;
        }
    }

    private static int findPath(int[][] capacity, boolean[] visited, int start, int end, int f) {
        if (start == end) {
            return f;
        }

        visited[start] = true;
        for (int v = 0; v < visited.length; v++)
            if (!visited[v] && capacity[start][v] > 0) {
                int dfs = findPath(capacity, visited, v, end, Math.min(f, capacity[start][v]));
                if (dfs > 0) {
                    capacity[start][v] -= dfs;
                    capacity[v][start] += dfs;
                    return dfs;
                }
            }
        return 0;
    }
}
