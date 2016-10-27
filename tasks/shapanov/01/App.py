'''
Created on Oct 25, 2016

@author: Iegor
'''
from GraphAnimator import GraphAnimator
from DataReader import DataReader
from GreedySolver import solve_tsp
import numpy as np

def make_dist_matrix(points):
    x = []
    y = []
    for point in points:
        x.append(point[0])
        y.append(point[1])
    """Creates distance matrix for the given coordinate vectors"""
    N = len(x)
    xx = np.vstack( (x,)*N )
    yy = np.vstack( (y,)*N )
    return np.sqrt( (xx - xx.T)**2 + (yy - yy.T)**2 )

if __name__ == "__main__":
    d = DataReader('../../capitals.txt')
    points = d.readPoints()
    matrix = make_dist_matrix(points)
    path = solve_tsp(matrix)
    g = GraphAnimator(points=points, pathes=path)
    g.beginAnimation()
    
