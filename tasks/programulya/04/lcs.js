/**
 * LCS - Longest common subsequence problem
 * Task: Find longest common subsequence of two strings
 */

function LCS(x, y) {
    const m = x.length;
    const n = y.length;
    const c = [];

    let i;
    let j;

    for (i = 0; i <= m; i++) {
        c.push([0]);
    }

    for (j = 0; j < n; j++) {
        c[0].push(0);
    }

    for (i = 0; i < m; i++)
        for (j = 0; j < n; j++) {
            if (x[i] === y[j]) {
                c[i + 1][j + 1] = c[i][j] + 1;
            } else {
                c[i + 1][j + 1] = Math.max(c[i + 1][j], c[i][j + 1]);
            }
        }

    return (function backtrack(i, j) {
        if (i * j === 0) {
            return '';
        }

        if (x[i - 1] === y[j - 1]) {
            return backtrack(i - 1, j - 1) + x[i - 1];
        }

        if (c[i][j - 1] > c[i - 1][j]) {
            return backtrack(i, j - 1);
        } else {
            return backtrack(i - 1, j);
        }
    })(m, n);
}

const result = LCS('ABCDEFG', 'BCDGK');
console.log(result);