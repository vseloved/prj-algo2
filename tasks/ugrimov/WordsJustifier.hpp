//
//  WordsJustifier.hpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef WordsJustifier_hpp
#define WordsJustifier_hpp

#include <stdio.h>
#include <map>
#include <vector>
#include <string>
#include <iostream>

class WordsJustifier {
    
    std::vector<std::string> words;
    std::map<int, std::string> strings;
    int limit;
    
    int* result{nullptr};
    
public:
    WordsJustifier(std::vector<std::string> words, int limit);
    ~WordsJustifier();
    void justifyDP();
    void justifyGreedy();
    
    void justifuProjector();
    
    void print();
    
};

#endif /* WordsJustifier_hpp */
