#define CATCH_CONFIG_MAIN
#include <catch.hpp>

#include "../impl/WordsAlignment.h"

using TWords = std::pair<std::string, std::string>;

#define WA_TEST(i_word1, i_word2, e_word1, e_word2)\
    {\
    TWords input_words = std::make_pair(i_word1, i_word2);\
    TWords expected_words = std::make_pair(e_word1, e_word2);\
    TWords actual_words = WordsAlignment::AlingWords(input_words.first, input_words.second);\
    REQUIRE(expected_words.first == actual_words.first);\
    REQUIRE(expected_words.second == actual_words.second);\
    }

TEST_CASE("WordsAlignment")
{

    SECTION("Test1")
    {
        WA_TEST("abc", "ac", "abc", "a_c");
    }
    SECTION("Test2")
    {
        WA_TEST("fyord", "world", "fyor_d", "_world");
    }
    SECTION("Test3")
    {
        WA_TEST("abcd", "abcd", "abcd", "abcd");
    }
    SECTION("Test4")
    {
        // taken from https://web.stanford.edu/class/cs124/lec/med.pdf
        //  changed to equivalent alignment in terms of edit distance
        WA_TEST("AGGCTATCACCTGACCTCCAGGCCGATGCCC",       "TAGCTATCACGACCGCGGTCGATTTGCCCGAC",
                "_AGGCTATCACCTGACCTCCAGGCCGAT__GCCC___", "TAG_CTATCAC__GACCGC__GGTCGATTTGCCCGAC");
    }
    SECTION("Test5")
    {
        WA_TEST("fyord", "worfld", "fyor__d", "_worfld");
    }
}