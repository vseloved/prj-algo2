const assert = require('assert');
const knapsack = require('../knapsack');

it('Test for 4 items', function() {
	const input = [{
		name: 'item1',
		w: 12,
		v: 24,
	},
	{
		name: 'item2',
		w: 7,
		v: 13,
	},
	{
		name: 'item3',
		w: 11,
		v: 23,
	},
	{
		name: 'item4',
		w: 8,
		v: 15,
	},
	{
		name: 'item5',
		w: 9,
		v: 16,
	}];
	const result = knapsack(input, 26);
	console.log(result)
	assert(result.length === 3);
	assert(result.some(x => x.name === 'item2'));
	assert(result.some(x => x.name === 'item3'));
	assert(result.some(x => x.name === 'item4'));
});