//
//  Utils.hpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef Utils_hpp
#define Utils_hpp

#include "App.hpp"

class Utils {
    
public:
    
    static int widthToScreen(float degree) {
        return ((degree - 3016.0) / 1686.0) * (App::SCREEN_WIDTH * 0.9f);
    }
    
    static int heightToScreen(float degree) {
        return ((degree - 811.0) / 11443.0) * (App::SCREEN_HEIGHT * 0.9f);
    }
    
    static char* getFileContents(const char* filename) {
        FILE* f = fopen(filename, "r");
        fseek(f, 0, SEEK_END);
        long fileLength = ftell(f) - 1;
        fseek(f, 0, SEEK_SET);
        
        char* data = new char[fileLength];
        fread(data, fileLength, 1, f);
        
        return data;
    }
    
    static void split(const std::string &s, char delim, std::vector<std::string> &elems) {
        std::stringstream ss;
        ss.str(s);
        std::string item;
        while (getline(ss, item, delim)) {
            item.erase(item.rfind("\r"));
            elems.push_back(item);
        }
    }
    
    static std::vector<std::string> split(const std::string &s, char delim) {
        std::vector<std::string> elems;
        split(s, delim, elems);
        return elems;
    }
    
};


#endif /* Utils_hpp */
