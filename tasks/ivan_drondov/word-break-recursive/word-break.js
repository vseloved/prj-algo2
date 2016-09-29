/**
 * Word break problem.
 * Author: Ivan Drondov
 * License: MIT
 */

function createAllSeq(word, suffixes) {
	const results = [];
	for(let i = 0; i < suffixes.length; ++i) {
		results.push(word + ' ' + suffixes[i]);
	}
	return results;
}

function wordBreak(str, dict, _memo) {
	const memo = _memo || {};
	if (memo[str]) return memo[str];
	const results = [];
	for(let i = 1; i < str.length - 1 ; ++i) {
		const word = str.slice(0, i);
		if (dict.has(word)) {
			const suffixes = wordBreak(str.slice(i), dict, memo);
			Array.prototype.push.apply(results, createAllSeq(word, suffixes));
		}
	}
	if (dict.has(str)) {
		results.push(str);
	}
	memo[str] = results;
	return results;
}

module.exports = wordBreak;