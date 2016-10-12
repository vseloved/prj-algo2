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
    
	result = new int[words.size()];
}

WordsJustifier::~WordsJustifier() {
    delete result;
}

void WordsJustifier::justifyDP() {
    int** cost = new int*[words.size()];
    for (int i = 0; i < words.size(); i++) {
        cost[i] = new int[words.size()];
    }
    
    for(int i = 0 ; i < words.size(); i++) {
        cost[i][i] = limit - words[i].size();
        for(int j=i+1; j < words.size(); j++){
            cost[i][j] = cost[i][j-1] - words[j].size() - 1;
        }
    }
    
    for(int i=0; i < words.size(); i++){
        for(int j=i; j < words.size(); j++){
            if (cost[i][j] < 0) {
                cost[i][j] = INT32_MAX;
            } else {
                cost[i][j] = pow(cost[i][j], 3);
            }
        }
    }
    
    //minCost from i to len is found by trying
    //j between i to len and checking which
    //one has min value
    int *minCost = new int[words.size()];
    
    for(int i = words.size() - 1; i >= 0 ; i--){
        minCost[i] = cost[i][words.size()-1];
        result[i] = words.size();
        for(int j = words.size() - 1; j > i; j--){
            if(cost[i][j-1] == INT32_MAX) {
                continue;
            }
            if(minCost[i] > minCost[j] + cost[i][j-1]){
                minCost[i] = minCost[j] + cost[i][j-1];
                result[i] = j;
            }
        }
    }
    
    for (int i = 0; i < words.size(); i++) {
        delete[] cost[i];
    }
    delete[] cost;
    delete[] minCost;
    delete[] result;
}

void WordsJustifier::justifyGreedy() {
    int amount = 0;
    int currentString = 0;
    int wordsInserted = 0;
    while (wordsInserted < words.size()) {
        if (amount + words[wordsInserted].size() > limit) {
	    	result[currentString] = wordsInserted;
            amount = 0;
            currentString++;
        }

        amount += words[wordsInserted].size();
        wordsInserted++;
    }
}


void WordsJustifier::justifuProjector() {
    
	    
    
}


void WordsJustifier::print() {
    int i = 0, j = 0;
    
    for (int i = 0; i < words.size(); i++) {
        std::cout<<result[i]<<" ";
    }
    
                      
    do{
        j = result[i];
        
        
        for(int k = i; k < j; k++){
            std::cout<<words[k]<<" ";
        }
        std::cout<<"\n";
        i = j;
    } while (j < words.size());
}








