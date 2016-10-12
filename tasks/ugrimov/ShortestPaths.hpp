//
//  ShortestPaths.hpp
//  Prjctr
//
//  Created by Artem on 09.10.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef ShortestPaths_hpp
#define ShortestPaths_hpp

#include <vector>
#include <initializer_list>


class UnweightedGraphSearch {
    static const int SIZE = 10;
    std::vector< std::vector<int> > matrix {
        //     0  1  2  3  4  5  6  7  8  9
        /*0*/{ 0, 1, 0, 0, 1, 1, 0, 0, 0, 1 },
        /*1*/{ 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
        /*2*/{ 0, 1, 0, 1, 0, 0, 0, 1, 0, 1 },
        /*3*/{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
        /*4*/{ 0, 0, 0, 1, 0, 1, 1, 0, 0, 0 },
        /*5*/{ 0, 0, 1, 1, 0, 0, 1, 1, 0, 0 },
        /*6*/{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        /*7*/{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
        /*8*/{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
        /*9*/{ 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 }
    };
    
    std::vector<bool> used;
    std::vector<int> p;
    std::vector<int> dist;
    
    void printWay(int u);
public:
    UnweightedGraphSearch();
    ~UnweightedGraphSearch();
    void process(int from, int to);
    
};




class DijkstraSearch {
    static const int SIZE = 10;
    std::vector< std::vector<std::pair<int, int> > > list {
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
    
    int findMinWeightIndex(std::vector<std::pair<int, int> > pairs);
    
public:
    DijkstraSearch() {};
    ~DijkstraSearch() {};
    
    void process(int from, int to);
};

#endif /* ShortestPaths_hpp */
