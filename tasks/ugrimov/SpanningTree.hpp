//
//  SpanningTree.hpp
//  Prjctr
//
//  Created by Artem on 15.10.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef SpanningTree_hpp
#define SpanningTree_hpp

#include <vector>
#include "Utils.hpp"

class SpanningTree {

    static const int SIZE = 10;
    
    std::vector<std::vector<std::pair<int, int> > > graph {
        { {1, 10}, {4, 3}, {5, 2}, {9, 12}},
        { {3, 2}, {4, 1} },
        { {1, 10}, {3, 10}, {7, 9}, {9, 9} },
        { {7, 2} },
        { {3, 3}, {5, 5}, {6, 10} },
        { {2, 4}, {3, 5}, {6, 5}, {7, 8} },
        { {0, 5}, {8, 1}, {9, 1} },
        { {0, 9}, {4, 15} },
        { {1, 2}, {2, 13}, {9, 13} },
        { {4, 5}, {5, 4} }
    };
    
    
    std::vector<int> verts;
    
    std::vector<Edge> result;
    
    int _find(int i);
    void _union(int i, int j);
    void _build();
public:
    SpanningTree();
};

#endif /* SpanningTree_hpp */
