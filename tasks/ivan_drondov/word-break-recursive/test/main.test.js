const assert = require('assert');
const wordBreak = require('../main');

it('Basic tests', function() {
	assert(wordBreak('thisistest').includes('this is test'));
});