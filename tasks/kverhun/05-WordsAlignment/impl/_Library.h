#pragma once

#if WORDS_ALIGNMENT_LIBRARY
#define WORDSALIGNMENT_API __declspec(dllexport)
#else
#define WORDSALIGNMENT_API __declspec(dllimport)
#endif
