//
//  Visualizer.h
//  
//
//  Created by Artem on 23.09.16.
//
//

#ifndef ____Visualizer__
#define ____Visualizer__

#include <stdio.h>

class Visualizer {
    
public :
    static void drawLine(float x1, float y1, float x2, float y2);
    static void drawPoint(float x, float y);
    static void drawCircle(float x, float y, float radius);
    static void drawRect(float x, float y, float w, float h);
};


#endif /* defined(____Visualizer__) */
