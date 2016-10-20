package com.ykushch.prjalgo2.task4;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EditDistanceTest {
    @Test
    public void shouldComputeMinEditDistanceWhenCorrectArgsPassed() throws Exception {
        String src = "world";
        String dest = "aafdfyord";
        int result = EditDistance.computeMinEditDistance(src, dest, true);
        assertThat(result).isEqualTo(7);
    }

}