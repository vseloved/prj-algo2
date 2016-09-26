//
//  App.cpp
//  Prjctr
//
//  Created by Artem on 23.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#include <thread>
#include <chrono>
#include "Visualizer.h"
#include "App.hpp"

App::App() {
    running = true;
    instance = this;
}

int App::onExecute() {
    if(OnInit() == false) {
        return -1;
    }
    
    SDL_Event Event;
    
    while(running) {
        while(SDL_PollEvent(&Event)) {
            OnEvent(&Event);
        }
        
        OnLoop();
        OnRender();
    }
    
    OnCleanup();
    
    return 0;
}

bool App::OnInit() {
    if(SDL_Init(SDL_INIT_EVERYTHING) < 0) {
        return false;
    }
    
    SDL_CreateWindowAndRenderer(SCREEN_WIDTH, SCREEN_HEIGHT, 0, &win, &renderer);
    if (!win) {
        return false;
    }
    
    int imgFlags = IMG_INIT_PNG;
    if( !( IMG_Init( imgFlags ) & imgFlags ) ) {
        return false;
    }
    
    if( TTF_Init() == -1 ) {
        return false;
    }
    
    return true;
}

void App::OnEvent(SDL_Event* Event) {
    switch (Event->type) {
        case SDL_QUIT:
            running = false;
            break;
        case SDL_KEYUP:
            delete wordsFinder;
            wordsFinder = new WordsFinder("thisisateststringtosearchhello");
            wordsFinder->fillWords();
            wordsFinder->print();
            
            //delete salesman;
            //salesman = new Solver();
            //salesman->solve();
            
            break;
    }
}

void App::OnLoop() {
}

void App::OnRender() {
    SDL_SetRenderDrawColor(App::getRenderer(), 0xFF, 0xFF, 0xFF, 0xFF );
	SDL_RenderClear(renderer);
    
    drawSalesman();
    
    SDL_RenderPresent(renderer);

}

void App::drawSalesman() {
    
    if (!salesman) {
        return;
    }
    
    SDL_SetRenderDrawColor(App::getRenderer(), 0x0, 0x0, 0xFF, 0xFF );
    auto& pointsToDraw = salesman->getPointsToDraw();
    for (int i = 0; i < pointsToDraw.size(); i++) {
        
        auto &p = pointsToDraw[i];
        Visualizer::drawPoint(p.x, p.y);
        
        if (i > 0) {
            auto& p2 = pointsToDraw[i-1];
            Visualizer::drawLine(p.x, p.y, p2.x, p2.y);
        }
    }
}

void App::OnCleanup() {
    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(win);
    
	SDL_Quit();
}



App* App::instance(nullptr);

App* App::getInstance() {
    return instance;
}

SDL_Renderer* App::getRenderer() {
    return getInstance()->renderer;
}
