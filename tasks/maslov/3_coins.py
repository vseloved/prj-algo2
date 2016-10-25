#!/usr/bin/env python
# -*- coding: utf-8 -*-

def coinChange(coinNeeded):
   coinValues = [1,3,7,13,29,50]
   # creating zeroed array in list
   minCoins = [[0 for j in range(coinNeeded + 1)]
               for i in range(len(coinValues))]
   # transforming first element of list array to range
   minCoins[0] = range(coinNeeded + 1)

   # trying coins valuse
   for i in range(1,len(coinValues)):
       # for first range
      for j in range(0, coinNeeded + 1):
          # if necessary value is less than current coin value
         if j < coinValues[i]:
             # form 2d array, with previous coin nominal as accepted one
            minCoins[i][j] = minCoins[i-1][j]
         else:
             # if necessary value is bigger than current coin value,
            minCoins[i][j] = min(minCoins[i-1][j],
             # form 2d array with current coin value as minimal
             1 + minCoins[i][j-coinValues[i]])

   return minCoins[-1][-1]

def main():
    print("Enter sum needed:")
    sumNeeded = int(input())
    print(coinChange(sumNeeded))

main()
