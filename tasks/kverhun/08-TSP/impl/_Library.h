#pragma once

#if TSP_LIBRARY
#define TSP_API __declspec(dllexport)
#else
#define TSP_API __declspec(dllimport)
#endif
