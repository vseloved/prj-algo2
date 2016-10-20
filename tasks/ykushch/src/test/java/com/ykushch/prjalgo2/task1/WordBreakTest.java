package com.ykushch.prjalgo2.task1;

import org.junit.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class WordBreakTest {
    @Test
    public void breakWordDp() throws Exception {
        WordBreak wordBreak = new WordBreak();
        Set<String> dict = Stream.of("greedy", "this", "is", "name", "code", "test", "a").collect(toSet());
        String sentence = wordBreak.breakWordWithLargeDictionary("thisisatest", dict);
        assertThat(sentence).isEqualTo("this is a test");
    }

    @Test
    public void shouldBreakSentenceIntoWordsWhenValidInputSent() throws Exception {
        WordBreak wordBreak = new WordBreak();
        Set<String> dict = Stream.of("greedy", "this", "is", "name", "code", "test", "a").collect(toSet());
        String sentence = wordBreak.breakWord("thisisatest", dict);
        assertThat(sentence).isEqualTo("this is a test");
    }

    @Test
    public void shouldBreakSentenceRecursivelyIntoWordsWhenValidInputSent() throws Exception {
        WordBreak wordBreak = new WordBreak();
        Set<String> dict = Stream.of("greedy", "this", "is", "name", "code", "test", "a").collect(toSet());
        String sentence = wordBreak.breakWordRecursively("thisisatest", dict);
        assertThat(sentence).isEqualTo("this is a test");
    }
}