#include <iostream>
#include <vector>
#include <math.h>
#include <algorithm>

struct Point
{
	double x;
	double y;
};

double DistanceSquare(const Point& i_pt1, const Point& i_pt2)
{
	double diff_x = i_pt1.x - i_pt2.x;
	double diff_y = i_pt1.y - i_pt2.y;
	return diff_x*diff_x + diff_y*diff_y;
}

size_t GetClosestPointIndex(const Point& i_point, const std::vector<Point>& i_points)
{
	std::vector<double> distances;
	distances.resize(i_points.size());
	for (size_t i = 0; i < i_points.size(); ++i)
		distances[i] = DistanceSquare(i_point, i_points[i]);

	double min_distance = distances[0];
	size_t closest_point_index = 0;

	for (size_t i = 1; i < i_points.size(); ++i)
		if (distances[i] < min_distance)
		{
			min_distance = distances[i];
			closest_point_index = i;
		}
	return closest_point_index;
}

std::vector<size_t> GetRouteIndexes(const std::vector<Point>& i_points)
{
	std::vector<size_t> route;
	//route.resize(i_points.size());
	route.push_back(0);

	for (size_t i = 1; i < i_points.size(); ++i)
	{
		std::vector<Point> points_to_process;
		for (size_t j = 0; j < i_points.size(); ++j)
			if (std::find(route.begin(), route.end(), j) == route.end())
				points_to_process.push_back(i_points[j]);

		size_t closest_point_index = GetClosestPointIndex(i_points[route.back()], points_to_process);
		route.push_back(closest_point_index);
	}
	return route;
}

int main()
{
	int point_number;
	std::cin >> point_number;

	std::vector<Point> points;
	points.resize(point_number);

	for (auto& pt : points)
	{
		int x, y;
		std::cin >> x >> y;
		pt.x = x;
		pt.y = y;
	}

	for (const auto& pt : points)
	{
		std::cout << "x: " << pt.x << "; y: " << pt.x << std::endl;
	}

	auto route = GetRouteIndexes(points);

	for (const auto& idx : route)
		std::cout << idx << std::endl;

	system("pause");

	return 0;
}