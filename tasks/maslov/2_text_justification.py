#!/usr/bin/env python
# -*- coding: utf-8 -*-

import math

justification_map = {}
min_map = {}

def total_length(str_arr):
    total = 0
    for string in str_arr:
        total = total + len(string)
    total = total + len(str_arr) - 1 # spaces
    return total

def badness(str_arr, page_width):
    line_len = total_length(str_arr)
    if line_len > page_width:
        return float('nan')
    else:
        return math.pow(page_width - line_len, 3)

def justify(i, n, words, page_width):
    if i == n:
        return 0
    ans = []
    for j in range(i+1, n+1):
        ans.append(justification_map[j]+ badness(words[i:j], page_width))
    min_map[i] = ans.index(min(ans)) + 1
    return min(ans)

def main():
    print("Enter page width")
    page_width = int(input())
    print("Enter text")
    paragraph = input()
    words = paragraph.split(' ')
    n = len(words)
    print("Total length is: ", total_length(words))
    print("Total number of words is: ", n)
    for i in reversed(range(n+1)):
        justification_map[i] = justify(i, n, words, page_width)
    print("Minimum badness achieved: ", justification_map[0])
    key = 0
    while(key <n):
        key = key + min_map[key]
        print(key)

    #print("Justified version is: ", )
if __name__ == '__main__':
    main()
