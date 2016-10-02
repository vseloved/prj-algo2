/**
 * Knapsack problem
 * Task: Find optimal set of things in bag below or equal to capacity
 */

function knapsack(items, capacity) {
    const itemsLength = items.length;
    const weightMatrix = [itemsLength + 1];
    const keepMatrix = [itemsLength + 1];
    const result = [];

    let weight = 0;
    let oldMax = 0;
    let newMax = 0;

    for (let i = 0; i < itemsLength + 1; i++) {
        weightMatrix[i] = [capacity + 1];
        keepMatrix[i] = [capacity + 1];
    }

    for (let j = 0; j <= itemsLength; j++) {
        for (weight = 0; weight <= capacity; weight++) {
            if (j === 0 || weight === 0) {
                weightMatrix[j][weight] = 0;
            } else if (items[j - 1].w <= weight) {
                newMax = items[j - 1].b + weightMatrix[j - 1][weight - items[j - 1].w];
                oldMax = weightMatrix[j - 1][weight];

                if (newMax > oldMax) {
                    weightMatrix[j][weight] = newMax;
                    keepMatrix[j][weight] = 1;
                } else {
                    weightMatrix[j][weight] = oldMax;
                    keepMatrix[j][weight] = 0;
                }
            } else {
                weightMatrix[j][weight] = weightMatrix[j - 1][weight];
            }
        }
    }

    weight = capacity;

    for (let k = itemsLength; k > 0; k--) {
        if (keepMatrix[k][weight] === 1) {
            result.push(items[k - 1]);
            weight = weight - items[k - 1].w;
        }
    }

    return {"maxValue": weightMatrix[itemsLength][capacity], "result": result};
}

var result = knapsack([{w: 12, b: 1}, {w: 1, b: 6}, {w: 6, b: 3}], 10);

console.log(result);