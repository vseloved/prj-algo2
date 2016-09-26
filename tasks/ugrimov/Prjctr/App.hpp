//
//  App.hpp
//  Prjctr
//
//  Created by Artem on 23.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef App_hpp
#define App_hpp

#include <SDL2/SDL.h>
#include <SDL2/SDL_ttf.h>
#include <SDL2/SDL_image.h>
#include <stdio.h>
#include "Solver.hpp"
#include "WordsFinder.hpp"

class App {

    static App* instance;
    static App* getInstance();
    
    bool running{false};
    SDL_Surface* screenSurface{nullptr};
    SDL_Window* win{nullptr};
    SDL_Renderer* renderer{nullptr};
    
    
    void drawSalesman();
    Solver* salesman{nullptr};
    
    WordsFinder* wordsFinder{nullptr};
    
public:
    
    static const int SCREEN_WIDTH {640};
    static const int SCREEN_HEIGHT {480};
    
    App();
    int onExecute();
    bool OnInit();
    void OnEvent(SDL_Event* Event);
    void OnLoop();
    void OnRender();
    void OnCleanup();

    static SDL_Renderer* getRenderer();
};

#endif /* App_hpp */
