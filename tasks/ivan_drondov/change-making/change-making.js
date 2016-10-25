const nominals = [1, 3, 7, 13, 29, 50];

function decode(args) {
	let currentIndex = args.length - 1;
	const result = [];
	while (currentIndex) {
		result.push(currentIndex - args[currentIndex]);
		currentIndex = args[currentIndex];
	}
	return result;
}

function changeMaking(n) {
	const mins = [0];
	const args = [0];
	// fill trivial cases.
	for (let i = 0; i < nominals.length; ++i) {
		mins[nominals[i]] = 1;
		args[i] = nominals[i];
	}
	// solve for all arguments less then n.
	for (let i = 1; i <= n; ++i) {
		let min = Infinity;
		let minArg = null;
		for (let j = 0; j < nominals.length; ++j) {
			const current = i - nominals[j];
			if (current < 0) continue;
			if (1 + mins[current] < min) {
				min = 1 + mins[current];
				minArg = current;
			}
		}
		args[i] = minArg;
		mins[i] = min;
	}
	return decode(args);
}

module.exports = changeMaking;