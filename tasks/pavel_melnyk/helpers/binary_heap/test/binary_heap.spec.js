'use strict';

let should = require('should');
let BinaryHeap = require('../index');

describe('BinaryHeap', function() {
    let arrayFixture = [10, 9, 0, 11, 16, 6, 5, -99, 1, 8, 4, 2, 1, -3];
    let heapOrdered = [ -99, 1, -3, 9, 4, 1, 0, 11, 10, 8, 16, 2, 6, 5 ]

    it('should create binary heap from array', function() {
        let bh = new BinaryHeap(arrayFixture);

        should(bh.getHeap()).be.eql(heapOrdered);
    });

    it('should create binary heap without array', function() {
        let bh = new BinaryHeap();

        should(bh.getHeap()).be.eql([]);
    });

    it('should return minimal number', function() {
        let bh = new BinaryHeap(arrayFixture);

        should(bh.extractMin()).be.eql(-99);
    })

    it('should remove number after #extractMin()', function() {
        let bh = new BinaryHeap(arrayFixture);

        should(bh.extractMin()).be.eql(-99);
        should(bh.getHeap()).have.lengthOf(arrayFixture.length - 1);
    })

    it('should rebuild heap after #decreaseKeyByIndex()', function() {
        let bh = new BinaryHeap(arrayFixture);

        bh.decreaseKeyByIndex(7, 8);
        should(bh.getHeap()[3]).be.eql(8);
    })

    it('should insert element to heap', function() {
        let bh = new BinaryHeap([10, 9, 0, 11, 16, 6, 5, -99, 1, 8, 4, 2, 1]);

        bh.insert(-3);
        should(bh.getHeap()).be.eql(heapOrdered);
    })

    it('should not fail when heap empty', function() {
        let bh = new BinaryHeap([10]);

        should(bh.extractMin()).eql(10);
        should(bh.extractMin()).be.eql(undefined)
    })

    it('should kip right order after multi insert', function() {
        let bh = new BinaryHeap();

        bh.insert(4);
        bh.insert(3);
        bh.insert(7);

        should(bh.extractMin()).eql(3);
    })

})
