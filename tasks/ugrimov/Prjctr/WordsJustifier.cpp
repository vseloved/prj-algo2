//
//  WordsJustifier.cpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#include "WordsJustifier.hpp"
#include <math.h>

//i = first word of the first line
//j = first word of th second line

WordsJustifier::WordsJustifier(std::vector<std::string> words, int limit) {
    this->words = words;
    this->limit = limit;
}

int WordsJustifier::uglyness(int firstWordIndex, int lastWordIndex) {
    int wordsLength = 0;
    for (int i = firstWordIndex; i <= lastWordIndex; i++) {
        wordsLength += words[i].size() + 1;
    }
    return pow(limit - firstWordIndex - lastWordIndex, 3);
}

void WordsJustifier::dp(int index) {
    
}
