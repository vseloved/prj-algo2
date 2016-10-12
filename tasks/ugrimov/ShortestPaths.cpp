//
//  ShortestPaths.cpp
//  Prjctr
//
//  Created by Artem on 09.10.16.
//  Copyright © 2016 Artem. All rights reserved.
//
#include <cstring>
#include <iostream>
#include <queue>
#include <vector>
#include "Queue.h"
#include "ShortestPaths.hpp"

UnweightedGraphSearch::UnweightedGraphSearch() :
used(SIZE, false),
p(SIZE, 0),
dist(SIZE, 0)
{

}

UnweightedGraphSearch::~UnweightedGraphSearch() {

}

void UnweightedGraphSearch::process(int u, int to) {
    
    used[u] = true;
    p[u] = u;
    dist[u] = 0;
    std::queue<int> q;
    q.push(u);
    while (!q.empty()) {
        int u = q.front();
        q.pop();
        for (int i = 0; i < (int) matrix[u].size(); i++) {
            int ind = matrix[u][i];
            if (ind) {
                int v = i;
            	if (!used[v]) {
                	used[v] = true;
	                p[v] = u;
    	            dist[v] = dist[u] + 1;
        	        q.push(v);
            	}
            }
        }
    }
    printWay(to);
}

void UnweightedGraphSearch::printWay(int u) {
    if (p[u] != u) {
        printWay(p[u]);
    }
    std::cout << u << ' ';
}





void DijkstraSearch::process(int from, int to) {
    std::vector<int> pathWeights(SIZE, INT_MAX);
    std::vector<bool> done(SIZE, false);
    pathWeights[from] = 0;
    
    Queue<int> verts;
    verts.add(from);
    
    while (!verts.empty()) {
        int current = verts.extract();
        if (!done[current]) {
    	    done[current] = true;
	        for (int i = 0; i < list[current].size(); i++) {
            	int closest = findMinWeightIndex(list[current]);
        	    int closestWeight = list[current][closest].second;
    	        int totalWeight = closestWeight + pathWeights[current];
                int vertNumber = list[current][closest].first;
	            if (pathWeights[vertNumber] > totalWeight) {
            		pathWeights[vertNumber] = totalWeight;
        	    }
                verts.add(vertNumber);
                list[current].erase(list[current].begin() + closest);
                i--;
	        }
        }
    }
    std::cout<<pathWeights[to]<<std::endl;
}

int DijkstraSearch::findMinWeightIndex(std::vector<std::pair<int, int> > pairs) {
    int min = 0;
    for (int i = 1; i < pairs.size(); i++) {
        if (pairs[i].second < pairs[min].second) {
            min = i;
        }
    }
    return min;
}





