
#include <catch.hpp>

#include "../impl/TSP.h"

#include <iostream>

namespace
{
    void _OutputMatrix(const TSP::TMatrix& i_matrix)
    {
        for (const auto& row : i_matrix)
        {
            for (const auto& el : row)
            {
                std::cout << el << '\t';
            }
            std::cout << std::endl;
        }
        std::cout << std::endl;
    }
}

TEST_CASE("TSP_InfrastructureTest")
{
    TSP::TPoints points = { { 1., 1. },{ 2., 2. },{ 2., 1. },{ 1., 2. } };
    
    TSP tsp(points);

    auto path = tsp.GenerateSomePath();
    REQUIRE(path.size() == points.size());

    auto pt1 = tsp.GetPoint(path[0]);
    for (size_t i = 0; i < points.size(); ++i)
    {
        REQUIRE(points[i].x == tsp.GetPoint(path[i]).x);
        REQUIRE(points[i].y == tsp.GetPoint(path[i]).y);
    }

}

TEST_CASE("TSP_Case1")
{
    TSP::TPoints points = { { 1., 1. },{ 2., 2. },{ 2., 1. },{ 1., 2. } };

    TSP tsp(points);

    for (auto i = 0; i < points.size(); ++i)
    {
        std::cout << i << ":\tx: " << points[i].x << ";\ty: " << points[i].y << std::endl;
    }

    SECTION("Distance checking")
    {
        TSP::TPath path_0213 = { 0, 2, 1, 3 };
        double length_0213 = tsp.GetPathLength(path_0213);
        REQUIRE(4. == length_0213);
    }

    SECTION("Solution checking")
    {
        auto path = tsp.GenerateSomePath();

        auto best_path = tsp.GenerateBestPathStartingFrom(path);

        double actual_path_lenght = tsp.GetPathLength(best_path);
        double expected_path_length = 4.;
        
        REQUIRE(expected_path_length == actual_path_lenght);

        for (auto id : best_path)
            std::cout << id;
        std::cout << std::endl << std::endl;
    }
}


TEST_CASE("TSP_Case2")
{
    TSP::TPoints points = { { 1., 1. },{ 1., 2. },{ 2., 1. },{ 2., 2. }, {3., 1.}, {3., 2.} };

    TSP tsp(points);

    auto path = tsp.GenerateSomePath();

    auto best_path = tsp.GenerateBestPathStartingFrom(path);

    for (auto i = 0; i < points.size(); ++i)
    {
        std::cout << i << ":\tx: " << points[i].x << ";\ty: " << points[i].y << std::endl;
    }

    double expected_path_length = 6.;
    double actual_path_length = tsp.GetPathLength(best_path);
    REQUIRE(expected_path_length == actual_path_length);

    for (auto id : best_path)
        std::cout << id;
    std::cout << std::endl << std::endl;

}

