#include "GeneticAlgo.h"

#include <algorithm>
#include <random>
#include <limits>
#include <iostream>

using namespace TSPGenetic;

GeneticSolver::GeneticSolver(const TSP & i_tsp)
    : m_tsp(i_tsp)
{
}

void GeneticSolver::InitializePopulation(size_t i_size)
{
    m_current_population = _GenerateRandomPopulation(i_size);
    _SortByFitness();
}

const GeneticSolver::TPopulation & GeneticSolver::GetCurrentPopulation() const
{
    return m_current_population;
}

void TSPGenetic::GeneticSolver::PerformSingleStep()
{

    const size_t g_num_new_paths = 50000;

    std::vector<TSP::TPath> new_paths;

    for (size_t i = 0; i < g_num_new_paths; i++)
    {
 //       std::cout << "launch crossover" << std::endl;
        auto new_path = _Crossover(m_current_population[2*i], m_current_population[2*i + 1]);
        new_paths.push_back(new_path);
    }

    size_t population_size = m_current_population.size();
    for (size_t i = 0; i < g_num_new_paths; ++i)
    {
        m_current_population[population_size - 1 - g_num_new_paths + i] = new_paths[i];
    }

    _SortByFitness();
}

GeneticSolver::TPopulation GeneticSolver::_GenerateRandomPopulation(size_t i_population_size)
{
    TSP::TPath path = m_tsp.GenerateSomePath();

    TPopulation random_population(i_population_size, path);

    for (auto& path : random_population)
    {
        std::random_device rd;
        std::mt19937 g(rd());

        std::shuffle(path.begin(), path.end(), g);
    }

    return random_population;
}

#include <iostream>
TSP::TPath GeneticSolver::_Crossover(const TSP::TPath & i_first, const TSP::TPath & i_second)
{
   // std::cout << "Crossover" << std::endl;
    size_t size = i_first.size();
 
    TSP::TPath result(size, std::numeric_limits<size_t>::max());
    //size_t taken_from_first = size / 2;
    std::random_device rd; // obtain a random number from hardware
    std::mt19937 eng(rd()); // seed the generator
    std::uniform_int_distribution<> distr(0, size);
    size_t taken_from_first = distr(eng);
   // std::cout << taken_from_first << std::endl;

    size_t cur_index = 0;
    while (cur_index < taken_from_first)
    {
        result[cur_index] = i_first[cur_index];
        ++cur_index;
    }
    
    for (const auto& id : i_second)
    {
        if (std::find(result.begin(), result.end(), id) == result.end())
        {
            result[cur_index] = id;
            ++cur_index;
        }
    }

    return result;
}

void TSPGenetic::GeneticSolver::_SortByFitness()
{
    std::sort(m_current_population.begin(), m_current_population.end(),
        [this](const TSP::TPath& i_left, const TSP::TPath& i_right) {return m_tsp.GetPathLength(i_left) < m_tsp.GetPathLength(i_right); }
    );
}
