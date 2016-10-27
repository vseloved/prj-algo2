#pragma once

#if LCS_LIBRARY
#define LCS_API __declspec(dllexport)
#else
#define LCS_API __declspec(dllimport)
#endif


