#include "TSP.h"

#include <iostream>
#include <algorithm>

namespace
{
    double _GetDistanceSquare(const TSP::Point& i_pt1, const TSP::Point& i_pt2)
    {
        double diff_x = i_pt1.x - i_pt2.x;
        double diff_y = i_pt1.y - i_pt2.y;
        return diff_x * diff_x + diff_y * diff_y;
    }

    void _OutputPathAndDistance(const TSP::TPath& i_path, double i_distance)
    {
        std::cout << "Trying path:" << std::endl;
        for (auto id : i_path)
        {
            std::cout << id << "\t";
        }
        std::cout << std::endl;
        std::cout << "Distance: " << i_distance << std::endl << std::endl;
    }
}

TSP::TSP(const TSP::TPoints& i_points)
    : m_points(i_points)
{
    size_t points_count = i_points.size();
    m_distance_square_matrix.resize(points_count);
    for (auto& row : m_distance_square_matrix)
        row.resize(points_count);

    for (size_t i = 0; i < points_count; ++i)
        for (size_t j = 0; j < points_count; ++j)
        {
            m_distance_square_matrix[i][j] = _GetDistanceSquare(i_points[i], i_points[j]);
        }
}

const TSP::TMatrix& TSP::GetDistanceMatrix() const
{
    return m_distance_square_matrix;
}

TSP::Point TSP::GetPoint(size_t i_index) const
{
    return m_points[i_index];
}

TSP::TPath TSP::GenerateSomePath() const
{
    TPath path;
    for (size_t i = 0; i < m_points.size(); ++i)
        path.push_back(i);
    return path;
}

TSP::TPath TSP::GenerateMutation(const TSP::TPath& i_path, size_t i, size_t j) const
{
    TPath new_path = i_path;

    std::reverse(new_path.begin() + i, new_path.begin() + j);

    return new_path;
}

double TSP::GetPathLength(const TSP::TPath& i_path) const
{
    double val = 0.;
    for (size_t i = 1; i < i_path.size(); ++i)
        val += m_distance_square_matrix[i_path[i-1]][i_path[i]];
    val += m_distance_square_matrix[i_path.front()][i_path.back()];
    return val;
}

TSP::TPath TSP::GenerateBetterPath(const TSP::TPath& i_path) const
{
    TPath current_best = i_path;
    double current_distance = GetPathLength(current_best);

    for (size_t i = 0; i + 1 < i_path.size(); ++i)
    {
        for (size_t j = i + 1; j < i_path.size(); ++j)
        {
            auto path = GenerateMutation(current_best, i, j);
            auto lng = GetPathLength(path);
            if (lng < current_distance)
            {
                current_distance = lng;
                current_best = path;
            }
        }
    }

    return current_best;
}

TSP::TPath TSP::GenerateBestPathStartingFrom(const TSP::TPath& i_path) const
{
    auto current_path = i_path;
    while (GenerateBetterPath(current_path) != current_path)
    {
        current_path = GenerateBetterPath(current_path);
    }
    return current_path;
}