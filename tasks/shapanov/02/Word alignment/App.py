INF = 1000000000
def alignment(wordlength, maximumlinewidth):
    N = len(wordlength) + 1
    n = len(wordlength)
    """matrix of extra spaces in lines"""
    spaces = [[0 for _ in range(N)] for _ in range(N)]
    """matrix of linecosts"""
    linecost = [[0 for _ in range(N)] for _ in range(N)]
    """C[i] will have total cost of optimal arrangement of words from 1 to i"""
    C = [0 for _ in range(N)]
    """p is used to print the solution"""
    p = [0 for _ in range(N)]
    i, j = 0, 0

    for i in range(1, N):
        spaces[i][i] = maximumlinewidth - wordlength[i - 1]
        for j in range(i+1, N):
            spaces[i][j] = spaces[i][j-1] - wordlength[j-1] - 1

    for i in range(1, N):
        for j in range(i, N):
            if spaces[i][j] < 0:
                linecost[i][j] = INF
            elif spaces[i][j] >= 0 and n == j:
                linecost[i][j] = 0
            else:
                linecost[i][j] = spaces[i][j]*spaces[i][j]

    """ Calculate minimum cost and find minimum cost arrangement.
      The value c[j] indicates optimized cost to arrange words
     from word number 1 to j."""

    for j in range(1, N):
        C[j] = INF
        for i in range(1, j+1):
            if C[i-1] != INF and linecost[i][j] != INF and (C[i - 1] + linecost[i][j] < C[j]):
                C[j] = C[i-1] + linecost[i][j]
                p[j] = i
    display(p, N - 1)

def display(p, N):
    k = 0
    if (p[N] == 1):
        k = 1
    else:
        k = display(p, p[N] - 1) + 1
    print("Line number {}: From word no. {} to {}".format(k, p[N], N))
    return k

def main():
    """
    example for aaa bb cc ddddd
    """
    l = [3, 2, 2, 5]
    M = 6
    alignment(l, M)

main()
