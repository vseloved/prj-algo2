function sum(array) {
	let value = 0;
	for (let i = 0; i < array.length; ++i) {
		value += array[i].v;
	}
	return value;
}

/**
 * Knapsack problem solution
 * w - weight of item.
 * v - value (profit) of item.
 * @param  {Array} items Array of items {name, w, v}.
 * @param  {Number} W    Maximum weight
 * @return {Array}       Optimal array of items in knapsack.
 */
function knapsack(items, W) {
	starts++;
	let optimal = null;
	let optimalValue = -Infinity;
	let optimalKnapsack = [];

	for(let i = 0; i < items.length; ++i) {
		if (items[i].w > W) continue;
		const currentKnapsack = knapsack(items.filter(x => x !== items[i]), W - items[i].w);
		const value = items[i].v + sum(currentKnapsack);

		if (value > optimalValue) {
			optimal = value;
			optimalValue = value;
			optimalKnapsack = currentKnapsack.concat([items[i]]);
		} 
	}
	return optimalKnapsack;
}

module.exports = knapsack;