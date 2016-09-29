const fs = require('fs');
const wordBreak = require('./word-break');
const dictArray = fs.readFileSync('../../dict_en.txt')
	.toString()
	.trim()
	.split(/\s+/);

const dict = new Set(dictArray);
if (process.argv[2]) {
	console.log(wordBreak(process.argv[2], dict).join('\n'));
}

module.exports = (str) => wordBreak(str, dict);