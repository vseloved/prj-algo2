/**
 * Binary heap
 * Task: Create binary heap
 */

class BinaryHeap {
    constructor(list) {
        this.list = list;

        for (let i = this.heapSize / 2; i >= 0; i--) {
            this.heapify(i);
        }
    }

    get heapSize() {
        return this.list.length;
    }

    extract() {
        let result = this.list[0];

        this.list[0] = this.list[this.heapSize - 1];
        this.list.splice(this.heapSize - 1, 1);

        return result;
    }

    add(value) {
        this.list.push(value);
        let i = this.heapSize - 1;
        let parent = (i - 1) / 2;

        while (i > 0 && this.list[parent] < this.list[i]) {
            let temp = this.list[i];
            this.list[i] = this.list[parent];
            this.list[parent] = temp;

            i = parent;
            parent = (i - 1) / 2;
        }
    }

    heapify(i) {
        let leftChild;
        let rightChild;
        let largestChild;

        for (; ;) {
            leftChild = 2 * i + 1;
            rightChild = 2 * i + 2;
            largestChild = i;

            if (leftChild < this.heapSize && this.list[leftChild] > this.list[largestChild]) {
                largestChild = leftChild;
            }

            if (rightChild < this.heapSize && this.list[rightChild] > this.list[largestChild]) {
                largestChild = rightChild;
            }

            if (largestChild == i) {
                break;
            }

            let temp = this.list[i];
            this.list[i] = this.list[largestChild];
            this.list[largestChild] = temp;

            i = largestChild;
        }
    }

    heapSort() {
        for (let i = this.list.length - 1; i >= 0; i--) {
            this.list[i] = this.extract();
            this.heapify(0);
        }
    }
}

const hip = new BinaryHeap([1, 4, 6, 7, 2]);
hip.add(0);

console.log(hip);