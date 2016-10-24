const lcs = require('../lcs');
const assert = require('assert');

it('Basic tests', function() {
	assert(lcs('world', 'fyord') === 4);
});