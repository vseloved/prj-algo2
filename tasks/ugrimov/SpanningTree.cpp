//
//  SpanningTree.cpp
//  Prjctr
//
//  Created by Artem on 15.10.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#include <iostream>
#include "SpanningTree.hpp"
#include "Queue.h"

SpanningTree::SpanningTree() {
    for (int i = 0; i < SIZE; i++) {
        verts.push_back(i);
    }
    _build();
}

int SpanningTree::_find(int i) {
    return verts[i];
}

void SpanningTree::_union(int with, int what) {
    int t = verts[with];
    if (t == verts[what]) {
        return;
    }
    for (int i = 0; i < verts.size(); i++) {
        if (verts[i] == t) {
            verts[i] = verts[what];
        }
    }
}

void SpanningTree::_build() {
    Queue<Edge> edges;
    for (int i = 0; i < graph.size(); i++) {
        for (auto& E : graph[i]) {
            edges.add(Edge(E.first, i, E.second));
        }
    }
    
    Edge e = edges.extract();
    _union(e.A, e.B);
    result.push_back(e);
    
    for (int i = 0; i < SIZE - 2; i++) {
        Edge e = edges.extract();
        if (_find(e.A) != _find(e.B)) {
            _union(e.A, e.B);
            result.push_back(e);
        }
    }
    
    //draw tree
    for (auto& edge : result) {
        std::cout<<edge.A<<" "<<edge.B<<" "<<"weight : "<<edge.weight<<std::endl;
    }
}







