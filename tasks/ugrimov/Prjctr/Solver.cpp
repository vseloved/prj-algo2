//
//  Solver.cpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#include "Solver.hpp"
#include <cstdlib>
#include <ctime>
#include <iostream>
#include "SpeedTest.hpp"
#include "Utils.hpp"

Solver::Solver() {
    
    readAllPoints();
}

void Solver::solve() {
    std::srand((unsigned int)std::time(0));
    int dice = std::rand() % (readPoints.size() - 1);
    
    speedtest__("Test time ") {
        
        float totalDistance = 0;
        auto p1 = readPoints[dice];
        for (std::size_t i = readPoints.size() - 1; i > 0 ; --i) {
            auto closest = findClosest(p1, readPoints);
            totalDistance += length(closest, p1);
            pointsToDraw.push_back(closest);
            p1 = closest;
        }
        pointsToDraw.push_back(p1);
        std::cout<<"Total distance "<<totalDistance<<std::endl;
    }
    std::cout<<std::endl;
}

void Solver::readAllPoints() {
    char* data = Utils::getFileContents("capitals.txt");
    std::string dataString(data);
    delete[] data;
    
    readPoints.clear();
    
    std::vector<std::string> cities = Utils::split(dataString, '\n');
    for (auto& city : cities) {
        std::vector<std::string> parts = Utils::split(city, ' ');
        
        std::size_t size = parts.size();
        float x = std::stof(parts[size - 2]);
        float y = std::stof(parts[size - 1]);
        std::string msg = size == 3 ? parts[0] : parts[0] + parts[1];
        
        readPoints.push_back(Point(x, y, msg));
    }
}

float Solver::sqr(float a) {
    return a * a;
}

float Solver::length(Point& p1, Point& p2) {
    return sqrtf(sqr(p2.x - p1.x) + sqr(p2.y - p1.y));;
}

Point Solver::findClosest(Point& point, std::vector<Point> &array) {
    
    Point closest = array[0];
    float currentLength = length(point, closest);
    int returnIndex = 0;
    for (int i = 0; i < array.size(); i++) {
        if (array[i] == point) continue;
        float nowLength = length(point, array[i]);
        if (nowLength < currentLength) {
            currentLength = nowLength;
            closest = array[i];
            returnIndex = i;
        }
    }
    array.erase(array.begin() + returnIndex);
    return closest;
}

const std::vector<Point>& Solver::getPointsToDraw() const {
    return pointsToDraw;
}
