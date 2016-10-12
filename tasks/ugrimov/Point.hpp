//
//  Point.hpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef Point_hpp
#define Point_hpp

#include <iostream>
#include <string>

class Point {
public:
    float x{0};
    float y{0};
    std::string msg;

    Point() {
        
    }
    
    Point(float x, float y, std::string message = "") {
        this->x = x;
        this->y = y;
        this->msg = message;
    }
    
    const bool cmp(const Point& other) const {
        return x == other.x && y == other.y;
    }
    
    void print() {
        std::cout<<"x = "<<x<<" y = "<<y<<std::endl;
    }
    
    bool operator==(const Point& other) {
        return cmp(other);
    }
    
    const Point& operator=(const Point& other) {
        x = other.x;
        y = other.y;
        return *this;
    }
    
    const Point operator+(const Point& other) {
        Point p(x + other.x, y + other.y);
        return p;
    }
};



#endif /* Point_hpp */
