const assert = require('assert');
const greedy = require('../greedy');

describe('Basic cases:', function() {
	it('should be correct for trivial', function() {
		assert(greedy([[1, 2], [5, 6], [3, 4]]).join() === [[1, 2], [3, 4], [5, 6]].join());
	});
});