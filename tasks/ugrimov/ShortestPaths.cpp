//
//  ShortestPaths.cpp
//  Prjctr
//
//  Created by Artem on 09.10.16.
//  Copyright © 2016 Artem. All rights reserved.
//
#include <cstring>
#include <cctype>
#include <iostream>
#include <queue>
#include <vector>
#include "Queue.h"
#include "Utils.hpp"
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


//==========================================================================================


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
                    paths[closest] = current;
        	    }
                
                verts.add(vertNumber);
                list[current].erase(list[current].begin() + closest);
                i--;
	        }
        }
    }
    
    //printWay(to);
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

void DijkstraSearch::printWay(int u) {
    if (paths[u] != u) {
        printWay(paths[u]);
    }
    std::cout << u << ' ';

}


//==========================================================================================


AStar::AStar() {
    readContent();
}

void AStar::readContent() {
    char* data = Utils::getFileContents("capitals-paths.txt");
    std::string dataString(data);
    delete[] data;
    
    std::vector<std::pair<std::string, std::string> > edges;
    
    std::vector<std::string> rows = Utils::split(dataString, '\n');
    int currentRow = 0;
    for (int i = 0; i < rows.size(); i++) {
        std::vector<std::string> cols = Utils::split(rows[i], ' ');
        
        if (i == 0) {
            for (auto& col : cols) {
                auto pair = Utils::split(col, '-');
                edges.push_back(std::make_pair(pair[0], pair[1]));
            }
            continue;
        }
        
        matrix.push_back(vector<int>());
        
        for (int j = 0; j < cols.size(); j++) {
            
            if (j == 0) {
                nameToNumber[cols[0]] = i - 1;
                numberToName[i - 1] = cols[0];
                continue;
            }
            
            if (cols[j].size() > 0) {
                matrix[currentRow].push_back(std::atoi(cols[j].c_str()));
            }
        }
        currentRow++;
    }

    for (int i = 0; i < nameToNumber.size(); i++) {
        list.push_back(std::vector<std::pair<int, int>>());
    }
    
    for (int i = 0; i < edges.size(); i++) {
        auto edge = edges[i];
        int from = nameToNumber[edge.first];
        int to = nameToNumber[edge.second];
        list[from].push_back(std::make_pair(to, matrix[from][i]));
    }
}

void AStar::process(int from, int to) {
    std::vector<int> pathWeights(list.size(), INT_MAX);
    std::vector<bool> done(list.size(), false);
    pathWeights[from] = 0;
    
    Queue<int> verts;
    verts.add(from);
    
    while (!verts.empty()) {
        int current = verts.extract();
        if (!done[current]) {
            done[current] = true;
            for (int i = 0; i < list[current].size(); i++) {
                int closest = heuristic(0, list[current].size()); //TODO
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

int AStar::heuristic(int from, int to) {
    return 0;
}































