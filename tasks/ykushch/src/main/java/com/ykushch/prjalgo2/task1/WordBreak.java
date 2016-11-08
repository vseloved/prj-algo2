package com.ykushch.prjalgo2.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class WordBreak {

    /**
     * Breaks words using naive approach (plain recursion)
     * @param sentence sentence without spaces
     * @param dict dictionary with unique words
     * @return sentence with spaces based on dictionary
     */
    public String breakWordRecursively(String sentence, Set<String> dict) {
        List<String> resultList = new ArrayList<>();
        couldBreakSentenceRecursively(sentence, dict, 0, resultList);
        Collections.reverse(resultList);
        return resultList.stream()
                .collect(joining(" "));
    }

    private boolean couldBreakSentenceRecursively(String sentence, Set<String> dict, int start,
                                                  List<String> resultList) {
        if(start == sentence.length()) {
            return true;
        }
        for(String word: dict) {
            int len = word.length();
            int end = start + len;
            if(end > sentence.length()) {
                continue;
            }

            String extractedWord = sentence.substring(start, end);
            if(extractedWord.equals(word)) {
                if(couldBreakSentenceRecursively(sentence, dict, start + len, resultList)) {
                    resultList.add(sentence.substring(start, start + len));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Breaks words using dynamic programming approach
     * @param s sentence without spaces
     * @param dict dictionary with unique words
     * @return sentence with spaces based on dictionary
     */
    public String breakWord(String s, Set<String> dict) {
        List<String> result = breakDownSentence(s, dict);
        return result.stream()
                .collect(joining(" "));
    }

    private List<String> breakDownSentence(String sentence, Set<String> dict) {
        List<String> resultBreakDownList = new ArrayList<>();
        boolean[] result = new boolean[sentence.length() + 1];
        result[0] = true;

        for (int i = 0; i < sentence.length(); i++) {
            if(!result[i]) {
                continue;
            }

            for (String wordFromDict : dict) {
                int len = wordFromDict.length();
                int end = i + len;

                if(end > sentence.length() || result[end]) {
                    continue;
                }

                String extractedWord = sentence.substring(i, end);
                if(extractedWord.equals(wordFromDict)) {
                    resultBreakDownList.add(wordFromDict);
                    result[end] = true;
                }
            }
        }

        return resultBreakDownList;
    }

    /**
     * Breaks words using dynamic programming approach. Case when dictionary is larger,
     * than the sentence itself.
     *
     * @param s sentence without spaces
     * @param dict dictionary with unique words
     * @return sentence with spaces based on dictionary
     */
    public String breakWordWithLargeDictionary(String s, Set<String> dict) {
        List<String> resultList = new ArrayList<>();
        List<String> memoList = new ArrayList<>();
        isSentenceBrokenIntoWords(s, dict, resultList, memoList);
        return resultList.stream()
                .collect(joining(" "));
    }

    private boolean isSentenceBrokenIntoWords(String sentence, Set<String> dict,
                                              List<String> resultList, List<String> memoList) {
        if(isSentenceValid(sentence)) {
            return false;
        }


        if(memoList.contains(sentence)) {
            return false;
        }

        String word = "";
        for(int i = 0; i < sentence.length(); i++) {
            word += sentence.charAt(i);
            if(dict.contains(word)) {
                resultList.add(word);
                String restPart = sentence.substring(i + 1);
                if(isSentenceBrokenIntoWords(restPart, dict, resultList, memoList)) {
                    return true;
                }
            }
        }

        memoList.add(sentence);
        return false;
    }

    private boolean isSentenceValid(String sentence) {
        return sentence == null || sentence.length() == 0;
    }
}
