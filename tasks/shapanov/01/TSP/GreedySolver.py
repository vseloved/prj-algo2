from __future__ import print_function, division
from itertools import islice
from array import array as pyarray
        
def restore_path( connections ):
    """Takes array of connections and returns a path.
    Connections is array of lists with 1 or 2 elements.
    These elements are indices of the vertices, connected to this vertex
    Guarantees that first index < last index
    """
    #there are 2 nodes with valency 1 - start and end. Get them.
    start, _ = [idx 
                  for idx, conn in enumerate(connections)
                  if len(conn)==1 ]
    path = [start]
    prev_point = None
    cur_point = start
    while True:
        next_points = [pnt for pnt in connections[cur_point] 
                       if pnt != prev_point ]
        if not next_points: break
        next_point = next_points[0]
        path.append(next_point)
        prev_point, cur_point = cur_point, next_point
    return path

def pairs_by_dist(N, distances):
    #Sort coordinate pairs by distance
    indices = [None] * (N*(N-1)//2)
    idx = 0
    for i in range(N):
        for j in range(i+1,N):
            indices[idx] = (i,j)
            idx += 1
            
    indices.sort(key = lambda ij: distances[ij[0]][ij[1]])
    return indices
    
def solve_tsp( distances, optim_steps=3, pairs_by_dist=pairs_by_dist ):
    """Given a distance matrix, finds a solution for the TSP problem.
    Returns list of vertex indices. 
    Guarantees that the first index is lower than the last"""
    N = len(distances)
    if N == 0: return []
    if N == 1: return [0]
    for row in distances:
        if len(row) != N: raise ValueError( "Matrix is not square")

    #State of the TSP solver algorithm.
    node_valency = pyarray('i', [2])*N #Initially, each node has 2 sticky ends
    
    #for each node, stores 1 or 2 connected nodes
    connections = [[] for _ in range(N)] 

    def join_segments(sorted_pairs):
        #segments of nodes. Initially, each segment contains only 1 node
        segments = [ [i] for i in range(N) ]
  
        def filtered_pairs():
            #Generate sequence of 
            for ij in sorted_pairs:
                i,j = ij
                if not node_valency[i] or\
                        not node_valency[j] or\
                        (segments[i] is segments[j]): 
                    continue
                yield ij

        for i,j in islice( filtered_pairs(), N-1 ):
            node_valency[i] -= 1
            node_valency[j] -= 1
            connections[i].append(j)
            connections[j].append(i)
            #Merge segment J into segment I.
            seg_i = segments[i]
            seg_j = segments[j]
            if len(seg_j) > len(seg_i):
                seg_i, seg_j = seg_j, seg_i
                i, j = j, i
            for node_idx in seg_j:
                segments[node_idx] = seg_i
            seg_i.extend(seg_j)

    join_segments(pairs_by_dist(N, distances))

    path = restore_path( connections )
    return path