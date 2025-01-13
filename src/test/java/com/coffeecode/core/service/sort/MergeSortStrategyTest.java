package com.coffeecode.core.service.sort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import com.coffeecode.core.models.*;

class MergeSortStrategyTest {
    
    @Test
    void shouldSortByAlphabeticalAndLength() {
        MergeSortStrategy sorter = new MergeSortStrategy();
        var entries = Arrays.asList(
            new DictionaryEntry("cat", "kucing"),
            new DictionaryEntry("cats", "kucing"),
            new DictionaryEntry("apple", "apel"),
            new DictionaryEntry("cat", "kucing")
        );

        SortResult result = sorter.sort(entries, Language.ENGLISH);
        var sorted = result.getSortedEntries();

        assertEquals("apple", sorted.get(0).getEnglish());
        assertEquals("cat", sorted.get(1).getEnglish());
        assertEquals("cat", sorted.get(2).getEnglish());
        assertEquals("cats", sorted.get(3).getEnglish());
        assertFalse(result.getSteps().isEmpty());
    }
}