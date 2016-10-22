/**
 * Justify Text.
 * Author: Ivan Drondov
 * License: MIT
 */

const DEFAULT_FILL = 0;

function createMatrix(n, m, fill) {
	const matrix = [];
	for(let i = 0; i < n; ++i) {
		matrix[i] = [];
		for(let j = 0; j < m; ++j) {
			matrix[i][j] = fill || DEFAULT_FILL;
		}
	}
	return matrix;
}

class Justifier {
	constructor(str, limit) {
		this.str = str;
		this.words = str.split(/\s+/);
		this.limit = limit;
		this.n = this.words.length;
		this.costMatrix = createMatrix(this.n, this.n, Infinity);
		this._fillCostMatrix();
		this.minCost = []; // from i to len optimal suffics cost.
		// If suffics with only one word then cost is equal to 
		// cost of one line with last word.
		this.minCost[this.n - 1] = this.costMatrix[this.n - 1][this.n - 1];
		this.result = [];
	}

	/**
	 * Fill cost matrix.
	 * This matrix represent cost of line started from word with index `i` to
	 * word with index `j` included.
	 */
	_fillCostMatrix() {
		outer: for(let i = 0; i < this.costMatrix.length; ++i) {
			for(let j = i; j < this.costMatrix[i].length; ++j) {
				let len = j - i; // Number of spaces for words from i to j;
				for(let k = i; k <= j; ++k) {
					len += this.words[k].length;
					if (len > this.limit) continue outer; // if more than limit, then Infinity.
				}
				this.costMatrix[i][j] = this.badness(len);
			}
		}
	}

	badness(len) {
		return Math.pow(this.limit - len, 3);
	}

	solve() {
		return this._solve(0);
	}

	getResult() {
		let result = '';
		let currentLine = [];
		let prevIndex = 0;
		let currentIndex = this.result[0];
		const words = this.words.slice();

		while(currentIndex) {
			currentLine = words.slice(prevIndex, currentIndex);
			prevIndex = currentIndex;
			currentIndex = this.result[currentIndex];
			result += currentLine.join(' ') + '\n';
		}
		result += words.slice(prevIndex).join(' ');
		return result;
	}

	_solve(index) {
		// Find the minimum of suffics cost from `index` to length. 
		if (Number.isFinite(this.minCost[index])) return this.minCost[index];
		let minCost = this.costMatrix[index][this.n - 1];
		let minCostIndex = this.n;
		for(let i = index + 1; i < this.n; ++i) {
			const currentCost = this._solve(i) + this.costMatrix[index][i - 1];
			if (currentCost < minCost) {
				minCost = currentCost;
				minCostIndex = i;
			}
		}
		this.minCost[index] = minCost;
		this.result[index] = minCostIndex;
		return minCost;
	}
}

module.exports = Justifier;