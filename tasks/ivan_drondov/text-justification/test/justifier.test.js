const assert = require('assert');
const Justifier = require('../justifier');

describe('Basic cases:', function() {

	it('should correct justify text', function() {
		const text = 'method for solving a complex problem by breaking it down into a collection of simpler subproblems';
		const justifier = new Justifier(text, 17);
		justifier.solve();
		assert(justifier.getResult() === `method for
solving a complex
problem by
breaking it down
into a collection
of simpler
subproblems`);

	});

	it('should correct justify text for two words', function() {
		const justifier = new Justifier('text test', 17);
		justifier.solve();
		const result = justifier.getResult();
		assert(justifier.getResult().trim() === 'text test');
	});
});