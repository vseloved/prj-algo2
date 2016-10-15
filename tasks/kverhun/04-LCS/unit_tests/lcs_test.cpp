#define CATCH_CONFIG_MAIN
#include <catch.hpp>

#include "../impl/lcs.h"

TEST_CASE("Words alignment", "[Words alignment]")
{
    SECTION("dummy test")
    {
        REQUIRE(1 == 1);
        //auto aligned_words = _AlingWords("abc", "ac");
        //REQUIRE(aligned_words.first == "abc");
        //REQUIRE(aligned_words.second == "a_c");
    }
}

TEST_CASE("LCS")
{
    //Tests::TestLCS test_lcs_1("abc", "ac", "ac");
    SECTION("Test 1")
    {
        REQUIRE(LCS::GetLCS("abc", "ac") == "ac");
    }

}