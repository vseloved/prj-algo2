function createMatrix(n, m, fill) {
	const matrix = [];
	for(let i = 0; i < n; ++i) {
		matrix[i] = [];
		for(let j = 0; j < m; ++j) {
			matrix[i][j] = fill || 0;
		}
	}
	return matrix;
}

function decode(a, b, result) {
	let i = a.length - 1;
	let j = b.length - 1;
	let str = '';
	let resultA = '';
	let resultB = '';
	while (i >= 0 && j >= 0) {
		let cur = result[i][j];
		if (cur === 'd' || cur === 'r') {
			if (cur === 'd') str = a[i] + str;
			resultA = a[i] + resultA;
			resultB = a[i] + resultB;
			i--;
			j--;
		} else if (result[i][j] === 'l') {
			resultB = '-' + resultB;
			resultA = a[i] + resultA;
			i--;
		} else if (result[i][j] === 't') {
			resultA = '-' + resultA;
			resultB = (b[j] || '-') + resultB;
			j--;
		}
	}
	return {
		lcs: str,
		a: resultA,
		b: resultB,
	};
}

/**
 * Compare Strings and return common string.
 * @param  {String} a
 * @param  {String} b
 */
function lcs(a, b) {
	// Fill matrix by zero's
	const matrix = createMatrix(a.length, b.length);
	const result = createMatrix(a.length, b.length);
	matrix[-1] = [];
	for (let i = -1; i < b.length; ++i) {
		matrix[-1][i] = 0;
	}
	for (let i = 0; i < a.length; ++i) {
		matrix[i][-1] = 0;
	}

	// Solve using dp.
	for (let i = 0; i < a.length; ++i) {
		for (let j = 0; j < b.length; ++j) {
			if (a[i] === b[j]) {
				result[i][j] = 'd';
				matrix[i][j] = matrix[i - 1][j - 1] + 1;
			} else {
				const operations = [{
					value: matrix[i - 1][j],
					op: 'l',
				}, {
					value: matrix[i][j - 1],
					op: 't'
				}, {
					value: matrix[i - 1][j - 1],
					op: 'r'
				}];
				operations.sort((x, y) => y.value - x.value);
				matrix[i][j] = operations[0].value;
				result[i][j] = operations[0].op;
			}
		}
	}
	return decode(a, b, result);
}

module.exports = lcs;