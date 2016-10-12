//
//  Solver.hpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef Solver_hpp
#define Solver_hpp

#include "Point.hpp"
#include <vector>
#include <math.h>
#include <sstream>
#include <string>
#include <iostream>


class Solver {
    
    float sqr(float a);
    float length(Point& p1, Point& p2);
    Point findClosest(Point& point, std::vector<Point> &array);
    
    std::vector<Point> pointsToDraw;
    std::vector<Point> readPoints;
    
public:
    Solver();
    void readAllPoints();
    void solve();
    const std::vector<Point>& getPointsToDraw() const;
};

#endif /* Solver_hpp */
