/**
 * Distance between two points.
 * Euclid metric.
 * Point [x, y]
 * @param  {Point} a
 * @param  {Point} b
 * @return {Number}  Distance.
 */
function dist(a, b) {
	return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
}

/**
 * Find optimal path from first point
 * @param  {Array} list  Array of points.
 * @return {Array}       Optimal path.
 */
function greedy(_list) {
	const list = _list.slice(1); // List without first point;
	let point = _list[0]; // current point;
	const result = [point];
	while (list.length) {
		let optimalIndex = -1;
		let optimalDist = Infinity;
		for(let i = 0; i < list.length; ++i) {
			const currentDist = dist(point, list[i]);
			if (currentDist < optimalDist) {
				optimalIndex = i;
				optimalDist = currentDist;
			}
		}
		point = list[optimalIndex];
		result.push(point);
		list.splice(optimalIndex, 1);
	}
	return result;
}

module.exports = greedy;
