'use strict';

let should = require('should');
let dijkstra = require('dijkstra');

describe('Dijkstra short path', function() {
    //[name,cost]
    let graph = [
        [[1,4],[2,3],[3,7]],//0
        [[4,5]],//1
        [[1,6],[4,11],[3,8]],//2
        [[4,2],[5,5]],//3
        [[5,10],[6,2]],//4
        [[6,3]],//5
        []//6
    ]

    it('should find shortest path (0,6)', function() {
        should(dijkstra(graph,0,6)).be.eql([ 0, 1, 4, 6 ]);
    });

    it('should find shortest path (0,5)', function() {
        should(dijkstra(graph,0,5)).be.eql([ 0, 3, 5 ]);
    });

});
