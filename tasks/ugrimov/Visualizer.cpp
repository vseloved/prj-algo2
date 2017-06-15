//
//  Visualizer.cpp
//  
//
//  Created by Artem on 23.09.16.
//
//

#include "Visualizer.h"
#include "App.hpp"
#include "Utils.hpp"
#include <math.h>
#include <SDL2/SDL.h>

void Visualizer::drawLine(float x1, float y1, float x2, float y2) {
    int xx1 = Utils::widthToScreen(x1);
    int xx2 = Utils::widthToScreen(x2);
    
    int yy1 = Utils::heightToScreen(y1);
    int yy2 = Utils::heightToScreen(y2);
    
    SDL_RenderDrawLine(App::getRenderer(), xx1, yy1, xx2, yy2);
    
    printf("%s\n", SDL_GetError());
}

void Visualizer::drawPoint(float x, float y) {
    drawRect(x - 2, y - 2, 4, 4);

}

void Visualizer::drawCircle(float x, float y, float radius) {
    int xx = Utils::widthToScreen(x);
    int yy = Utils::heightToScreen(y);
    
    int sides = 180;
    float angle = 3.141592653589793f / sides;
    float currentAngle = 0;
    
    Point start, end, center(xx, yy);
    end.x = radius;
    end.y = 0.0f;
    end = end + center;
    for (int i = 0; i != sides; i++)
    {
        start = end;
        end.x = cos(angle) * radius;
        end.y = sin(angle) * radius;
        end = end + center;
        currentAngle += angle;
        drawLine(start.x, start.y, end.x, end.y);
    }
}

void Visualizer::drawRect(float x, float y, float w, float h) {
    int xx1 = Utils::widthToScreen(x);
    int yy1 = Utils::heightToScreen(y);
    
    SDL_Rect r;
    r.x = xx1;
    r.y = yy1;
    r.w = w;
    r.h = h;
    
    SDL_RenderDrawRect(App::getRenderer(), &r);
}





