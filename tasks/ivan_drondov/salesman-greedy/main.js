const fs = require('fs');
const greedy = require('./greedy');
const drawList = require('./draw');

const DEFAULT_PATH = '../../capitals.txt';

const list = fs.readFileSync(process.argv[2] || DEFAULT_PATH)
	.toString()
	.trim()
	.split('\n')
	.map(x => x.replace(/^[a-z\s\]]+/i, ''))
	.map(x => x.split(' ').map(x => +x));

const resultPath = (process.argv[3] || __dirname) + '/result.png';

const path = greedy(list);
drawList(path, resultPath);
