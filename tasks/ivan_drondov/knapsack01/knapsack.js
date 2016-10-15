function createMatrix(n, m, fill) {
	const matrix = [];
	for (let i = 0; i < n; ++i) {
		matrix[i] = [];
		for (let j = 0; j < m; ++j) {
			matrix[i][j] = fill || 0;
		}
	}
	return matrix;
}

function sum(array) {
	let value = 0;
	for (let i = 0; i < array.length; ++i) {
		value += array[i].v;
	}
	return value;
}

/**
 * Find optimal knapsack from matrix
 * @param  {Matrix} matrix Matrix of cost
 * @param  {Array} items   Array of all items.
 * @return {Array}         Array of optimal items.
 */
function decode(matrix, items) {
	let w = matrix.length - 1;
	let i = matrix[w].length - 1;
	const result = [];
	while (i) {
		/**
		 * if optimal result for weight w with i items equal to
		 * optimal result with i - 1 items, then i-th items not in
		 * optimal set.
		 */
		if (matrix[w][i] !== matrix[w][i - 1]) {
			result.push(items[i]);
			w -= items[i].w;
		}
		i--;
	}
	return result;
}

/**
 * Knapsack problem solution
 * w - weight of item.
 * v - value (profit) of item.
 * @param  {Array} items Array of items {name, w, v}.
 * @param  {Number} W    Maximum weight
 * @return {Array}       Optimal array of items in knapsack.
 */
function knapsack(items, W) {
	// Search for maximum possible value.
	const matrix = createMatrix(W + 1, items.length);

	// For 0 items optimal is 0.
	for (let i = 0; i <= W; ++i) {
		matrix[i][0] = 0;
	}

	for (let w = 0; w <= W; ++w) {
		for (let i = 1; i < items.length; ++i) {
			if (items[i].w > w) {
				matrix[w][i] = matrix[w][i - 1];
				continue;
			}
			matrix[w][i] = Math.max(matrix[w - items[i].w][i - 1] + items[i].v, matrix[w][i - 1]);
		}
	}

	return decode(matrix, items);
}

module.exports = knapsack;