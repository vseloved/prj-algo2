#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define min(a, b) (((a) < (b)) ? (a) : (b))
#define max(a, b) (((a) > (b)) ? (a) : (b))

#define MATCH 0
#define INSERT 1
#define DELETE 2

int match(char c, char p) {
    return !(c == p);
}

int edit_distance(char * t, char * p, int i, int j) {
    if (i == 0)
        return j;
    if (j == 0)
        return i;
    int _mtch = edit_distance(t, p, i - 1, j - 1) + match(t[i - 1], p[j - 1]);
    int _ins = edit_distance(t, p, i -1, j) + 1;
    int _del = edit_distance(t, p, i, j - 1) + 1;
    return min(_mtch, min(_ins, _del));
}

typedef struct {
    int cost;
    int parent;
} cell;

cell dp[100 + 1][100 + 1];

void insert_out(char *s, int j) {
    printf("I");
}

void delete_out(char *s, int i) {
    printf("D");
}

void match_out(char *s1, char *s2, int i, int j) {
    if (s1[i] == s2[j])
        printf("M");
    else
        printf("S");
}

void restore_path(char *s1, char *s2, int i, int j) {
    if (dp[i][j].parent == -1)
        return;
    if(dp[i][j].parent == MATCH) {
        restore_path(s1, s2, i -1, j - 1);
        match_out(s1, s2, i - 1, j - 1);
        return;
    }
    if (dp[i][j].parent == INSERT) {
        restore_path(s1, s2, i, j - 1);
        insert_out(s2, j);
        return;
    }
    if (dp[i][j].parent == DELETE) {
        restore_path(s1, s2, i - 1, j );
        delete_out(s1, i);
        return;
    }
}


int edit_distance_DP(char * s1, char * s2) {
    int m = strlen(s1);
    int n = strlen(s2);
    int opt[3];
    for (int i = 0; i <= m; ++i) {
        dp[0][i].cost = i;
        dp[0][i].parent = i > 0 ? INSERT : -1;
    }
    for (int j = 0; j <= n; ++j) {
        dp[j][0].cost = j;
        dp[j][0].parent = j > 0 ? DELETE : -1;
    }
    for (int i = 1; i <= m; ++i) {
        for (int j = 1; j <= n; ++j) {
            opt[MATCH] =  dp[i -1][j - 1].cost + match(s1[i - 1], s2[j - 1]);
            opt[INSERT] = dp[i][j - 1].cost + 1;
            opt[DELETE] = dp[i - 1][j].cost + 1;
            dp[i][j].cost = opt[MATCH];
            dp[i][j].parent = MATCH;
            for (int k = INSERT; k <= DELETE; k++) {
                if (opt[k] < dp[i][j].cost) {
                    dp[i][j].cost = opt[k];
                    dp[i][j].parent = k;
                }
            }
        }
    }
    restore_path(s1, s2, m, n);
    return dp[m][n].cost;
}

int main() {
    char _t[] = "world";
    char _p[] = "fyord";
    printf("\n%d\n",edit_distance_DP(_t, _p));
    return 0;
}
