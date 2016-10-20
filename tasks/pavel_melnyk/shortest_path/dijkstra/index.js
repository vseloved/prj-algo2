'use strict';

let BHeap = require('../../helpers/binary_heap');

function processVertex(opt) {
    let path = opt.paths[opt.nextMinStepCost].shift() || [];
    let processedVertex = path[path.length-1];

    if( opt.viseted.indexOf(processedVertex) > -1 ) {
        return;
    }
    opt.viseted.push(processedVertex);

    opt.graph[processedVertex].forEach(vertex => {
        let newCost = opt.nextMinStepCost + vertex[1];
        let newPath = path.concat(vertex[0])

        opt.paths[newCost] = opt.paths[newCost] || [];
        opt.paths[newCost].push(newPath);

        opt.heap.insert(newCost);

        if(vertex[0] === opt.end && newCost < opt.finalPathCost) {
            opt.finalPathCost = newCost;
            opt.finalPath = newPath;
        }
    })
}

function dijkstra(graph, start, end) {
    var opt = {
        graph,
        paths: {
            0: [[start]]
        },
        start,
        end,
        heap: new BHeap(),
        viseted: [],
        nextMinStepCost: 0,
        finalPathCost: Infinity,
        finalPath: []
    }

    processVertex(opt);
    opt.nextMinStepCost = opt.heap.extractMin();

    while(opt.nextMinStepCost !== undefined || opt.finalPathCost > opt.nextMinStepCost) {
        processVertex(opt);
        opt.nextMinStepCost = opt.heap.extractMin();
    }

    return opt.finalPath;
}

module.exports = dijkstra;
