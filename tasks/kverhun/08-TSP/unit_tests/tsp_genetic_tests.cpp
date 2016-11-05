#define CATCH_CONFIG_MAIN
#include <catch.hpp>

#include "../impl/TSP.h"
#include "../impl/GeneticAlgo.h"

#include <iostream>

using namespace TSPGenetic;

namespace
{
    void _OutputPathAndDistance(const TSP::TPath& i_path, double i_distance)
    {
        std::cout << "Path:\t";
        for (auto id : i_path)
        {
            std::cout << id << "\t";
        }
        std::cout << std::endl;
        std::cout << "Distance: " << i_distance << std::endl << std::endl;
        
    }
}

TEST_CASE("Genetic_TestCase1")
{
    REQUIRE(1 == 1);

    TSP::TPoints points = { { 1., 1. },{ 1., 2. },{ 2., 1. },{ 2., 2. },{ 3., 1. },{ 3., 2. } };
    TSP tsp(points);

    GeneticSolver solver(tsp);
    solver.InitializePopulation(10);

    REQUIRE(solver.GetCurrentPopulation().size() == 10);

    for (const auto& path : solver.GetCurrentPopulation())
        _OutputPathAndDistance(path, tsp.GetPathLength(path));

    std::cout << "==========================" << std::endl;
    solver.PerformSingleStep();
    for (const auto& path : solver.GetCurrentPopulation())
        _OutputPathAndDistance(path, tsp.GetPathLength(path));

    std::cout << "==========================" << std::endl;
    solver.PerformSingleStep();
    for (const auto& path : solver.GetCurrentPopulation())
        _OutputPathAndDistance(path, tsp.GetPathLength(path));
    std::cout << "==========================" << std::endl;

    solver.PerformSingleStep();
    for (const auto& path : solver.GetCurrentPopulation())
        _OutputPathAndDistance(path, tsp.GetPathLength(path));
    std::cout << "==========================" << std::endl;
    solver.PerformSingleStep();
    for (const auto& path : solver.GetCurrentPopulation())
        _OutputPathAndDistance(path, tsp.GetPathLength(path));
    std::cout << "==========================" << std::endl;
    solver.PerformSingleStep();
    for (const auto& path : solver.GetCurrentPopulation())
        _OutputPathAndDistance(path, tsp.GetPathLength(path));
    std::cout << "==========================" << std::endl;
    solver.PerformSingleStep();
    for (const auto& path : solver.GetCurrentPopulation())
        _OutputPathAndDistance(path, tsp.GetPathLength(path));
    std::cout << "==========================" << std::endl;


}


TEST_CASE("Capitals")
{
    TSP::TPoints pts = {
        {3222, 8618  },
        {3326, 1125  },
        {3444, 9217  },
        {3834, 12129 },
        {3944, 10459 },
        {4145, 7240  },
        {3990, 7531  },
        {3026, 8416  },
        {3344, 8423  },
        {4337, 11611 },
        {3947, 8939  },
        {3946, 8690  },
        {4135, 9336  },
        {3920, 9540  },
        {3811, 8452  },
        {3027, 9111  },
        {4418, 6946  },
        {3858, 7629  },
        {4221, 7130  },
        {4244, 8433  },
        {4457, 9360  },
        {3218, 9010  },
        {3834, 9210  },
        {4635, 1121  },
        {4048, 9641  },
        {3990, 11945 },
        {4312, 7132  },
        {4013, 7446  },
        {3540, 10556 },
        {4239, 7345  },
        {3546, 7838  },
        {4649, 10046 },
        {3957, 8259  },
        {3529, 9730  },
        {4456, 1231  },
        {4015, 7652  },
        {4149, 7124  },
        {3400, 811   },
        {4422, 10020 },
        {3609, 8647  },
        {3016, 9744  },
        {4046, 11153 },
        {4415, 7234  },
        {3732, 7726  },
        {4702, 12254 },
        {3820, 8136  },
        {4304, 8923  },
        {4108, 10449 }
    };

    TSP tsp(pts);

    GeneticSolver solver(tsp);
    solver.InitializePopulation(100000);

    const size_t iterations = 100;

    std::vector<double> lengths(iterations);

    for (size_t i = 0; i < iterations; ++i)
    {
        solver.PerformSingleStep();
        //for (const auto& path : solver.GetCurrentPopulation())
            //_OutputPathAndDistance(solver.GetCurrentPopulation().front(), tsp.GetPathLength(solver.GetCurrentPopulation().front()));
        //std::cout << "==========================" << std::endl;
        lengths[i] = tsp.GetPathLength(solver.GetCurrentPopulation().front());
    }

    for (auto lng : lengths)
    {
        std::cout << lng << std::endl;
    }

}


