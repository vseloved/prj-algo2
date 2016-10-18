'use strict';

let should = require('should');
let dijkstra = require('dijkstra');

describe('Dijkstra short path', function() {
    //[name,cost]
    let graph1 = [
        [[1,4],[2,3],[3,7]],//0
        [[4,5]],//1
        [[1,6],[4,11],[3,8]],//2
        [[4,2],[5,5]],//3
        [[5,10],[6,2]],//4
        [[6,3]],//5
        []//6
    ]

    let graph2 = [
        [ [1, 10], [4, 3], [5, 2], [9, 12] ],
        [ [3, 2], [4, 1] ],
        [ [1, 10], [3, 10], [7, 9], [9, 9] ],
        [ [7, 2] ],
        [ [3, 3], [5, 5], [6, 10] ],
        [ [2, 4], [3, 5], [6, 5], [7, 8] ],
        [ [0, 5], [8, 1], [9, 1] ],
        [ [0, 9], [4, 15] ],
        [ [1, 2], [2, 13], [9, 13] ],
        [ [4, 5], [5, 4] ]
    ];

    it('should find shortest path, costome graph (0,6)', function() {
        should(dijkstra(graph1, 0, 6)).be.eql([ 0, 1, 4, 6 ]);
    });

    it('should find shortest path, given graph (0,8)', function() {
        should(dijkstra(graph2, 0, 8)).be.eql([ 0, 5, 6, 8 ]);
    });

});
