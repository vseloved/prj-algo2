//
//  WordsFinder.hpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef WordsFinder_hpp
#define WordsFinder_hpp

#include <vector>
#include <string>

class WordsFinder {

    std::vector<std::string> words;
    std::vector<std::string> dictionary;
    std::string input;
    std::string output;
    
    std::vector<std::string> getWordsWhereNletterIs(std::vector<std::string>& dict, const char letter, int position);
    bool isWord(std::string word);
    void catchWord(std::string& word);
    
public:
    WordsFinder(std::string textToSplit);
    void fillWords();
    void print();
    
};

#endif /* WordsFinder_hpp */
