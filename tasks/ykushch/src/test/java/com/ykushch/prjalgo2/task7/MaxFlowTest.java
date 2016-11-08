package com.ykushch.prjalgo2.task7;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxFlowTest {
    @Test
    public void shouldFindMaxFlowWhenCorrectGraphIsPassed() throws Exception {
        int[][] capacity = {
                {0, 3, 2},
                {0, 0, 2},
                {0, 0, 0}
        };

        int maxFlow = MaxFlow.findMaxFlow(capacity, 0, 2);
        assertThat(maxFlow).isEqualTo(4);
    }

}