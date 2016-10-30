#pragma once

#include "_Library.h"

#include <vector>

class TSP_API TSP
{
public:
    struct Point
    {
        double x;
        double y;
    };

    using TPoints = std::vector<Point>;
    using TMatrix = std::vector<std::vector<double>>;

    using TPath = std::vector<size_t>;
public:
    TSP(const TPoints& i_points);

    const TMatrix& GetDistanceMatrix() const;

    Point GetPoint(size_t i_index) const;
    const TPoints& GetPoints() const;

    TPath GenerateSomePath() const;

    TPath GenerateMutation(const TPath& i_path, size_t i, size_t j) const;

    TPath GenerateBetterPath(const TPath& i_path) const;
    TPath GenerateBestPathStartingFrom(const TPath& i_path) const;

    double GetPathLength(const TPath& i_path) const;

private:
    TPoints m_points;
    TMatrix m_distance_square_matrix;

};