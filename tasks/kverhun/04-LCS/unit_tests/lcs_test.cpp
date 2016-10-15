#define CATCH_CONFIG_MAIN
#include <catch.hpp>

#include "../impl/lcs.h"

TEST_CASE("LCS")
{
    SECTION("Test 1")
    {
        REQUIRE(LCS::GetLCS("abc", "ac") == "ac");
    }

    SECTION("Test 2")
    {
        REQUIRE(LCS::GetLCS("fyord", "world") == "ord");
    }
    SECTION("Test 3")
    {
        REQUIRE(LCS::GetLCS("abcd", "abcd") == "abcd");
    }
    SECTION("Test 4")
    {
        REQUIRE(LCS::GetLCS("fyord", "worfld") == "ord");
    }

}