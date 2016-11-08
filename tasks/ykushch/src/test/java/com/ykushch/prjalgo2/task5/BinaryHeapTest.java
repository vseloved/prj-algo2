package com.ykushch.prjalgo2.task5;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class BinaryHeapTest {

    @Test
    public void shouldCorrectlyBuildBinaryHeapMaxWhenUnorderedArrayIsPassed() throws Exception {
        BinaryHeap binaryHeap = new BinaryHeap();
        int[] unsortedArray = {3, 2, 1, 7, 8, 4, 10, 16, 12};
        int[] expected = {16, 12, 10, 7, 8, 4, 1, 3, 2};
        binaryHeap.buildHeap(unsortedArray);
        Assertions.assertThat(binaryHeap.getBinaryHeap()).isEqualTo(expected);
    }
}