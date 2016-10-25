const lcs = require('./lcs');

if (process.argv[2] && process.argv[3]) {
	const a = '' + process.argv[2];
	const b = '' + process.argv[3];
	const result = lcs(a, b);
	console.log(result.a);
	console.log(result.b);
	console.log(`LCS: ${result.lcs}`);
} else {
	console.log('Undefined input. Please provide two strings.')
}