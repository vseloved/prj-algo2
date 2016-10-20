'use strict';

class BinaryHeap {
    constructor(arr) {
        if(!arr || !arr.length) {
            this.heap = [];
            return;
        }

        this.heap = arr.slice();
        this.buildHeap();
    }
    getHeap() {
        return this.heap;
    }
    parrentIndex(i) {
        return Math.floor(i/2) + (i%2) - 1;
    }
    change(a, b) {
        let mem = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = mem;
    }
    heapify(i) {
        if(this.heap.length <= 1 || i >= this.heap.length) return;

        while(i < this.heap.length) {
            let leftChildIndex = 2*i+1;
            let rightChildIndex = 2*i+2;
            let min = i;

            if(leftChildIndex < this.heap.length && this.heap[leftChildIndex] < this.heap[min]) {
                min = leftChildIndex;
            }
            if(rightChildIndex < this.heap.length && this.heap[rightChildIndex] < this.heap[min]) {
                min = rightChildIndex;
            }
            if(min === i) {
                return;
            }

            this.change(i, min);
            i = min;
        }

        console.log('some troble with heapify');
    }
    buildHeap() {
        for(let i = Math.floor(this.heap.length/2); i >= 0; i--) {
            this.heapify(i);
        }
    }
    extractMin() {
        if(this.heap.length === 0) return;

        let min = this.heap[0];
        this.heap[0] = this.heap[this.heap.length-1];
        this.heap.pop();
        this.heapify(0);
        return min;
    }
    decreaseKeyByIndex(i, key) {
        if(i > this.heap.length) {
            return;
        }

        if(key > this.heap[i]) return console.log('can\'t increase key');

        let parrentIndex = this.parrentIndex(i);
        this.heap[i] = key;

        while(i > 0 && this.heap[parrentIndex] > this.heap[i]) { //i > 0
            this.change(parrentIndex, i)
            i = this.parrentIndex(i);
            parrentIndex = this.parrentIndex(i);
        }

    }
    insert(element) {
        this.heap.push(element);
        this.decreaseKeyByIndex(this.heap.length-1, element);
    }

}

module.exports = BinaryHeap;
