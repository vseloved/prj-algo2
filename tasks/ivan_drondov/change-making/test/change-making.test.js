const assert = require('assert');
const changeMaking = require('../change-making');

it('Basic tests', function() {
	assert(changeMaking(58).join() === [29, 29].join());
});