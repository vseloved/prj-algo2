//
//  WordsFinder.cpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#include <algorithm>
#include "WordsFinder.hpp"
#include "Utils.hpp"

WordsFinder::WordsFinder(std::string inputString) {
    char * dict = Utils::getFileContents("dict_en.txt");
    std::string dictString(dict);
    dictionary = Utils::split(dictString, '\n');
    input = inputString;
}

void WordsFinder::fillWords() {
    std::string currentWord = "";
    int currentSymbol = 0;
    int currentWordSymbol = 0;
    std::size_t textLength = input.size();
    
    auto filter = dictionary;
    while (currentSymbol < textLength) {
        currentWord += input[currentSymbol];
        filter = getWordsWhereNletterIs(filter, currentWord[currentWordSymbol], currentWordSymbol);
        if (filter.size() == 1) {
            catchWord(currentWord);
            currentWordSymbol = -1;
            filter = dictionary;
        } else if (filter.size() == 0) {
            currentWord.erase(currentWord.end() - 1);
            catchWord(currentWord);
            currentSymbol--;
            currentWordSymbol = -1;
            filter = dictionary;
        }
        currentSymbol++;
        currentWordSymbol++;
    }
}

void WordsFinder::catchWord(std::string &word) {
    output += word;
    output += " ";
    word = "";
}

void WordsFinder::print() {
    std::cout<<output<<std::endl;
}

std::vector<std::string> WordsFinder::getWordsWhereNletterIs(std::vector<std::string> &dict, const char letter, int position) {
    std::vector<std::string> output;
    for (auto& word : dict) {
        if (word[position] == letter) {
            output.push_back(word);
        }
    }
    return output;
}






