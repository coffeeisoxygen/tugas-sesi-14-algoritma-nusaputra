package com.coffeecode.core.service.search;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

class BinarySearchStrategyTest {
    
    @Test
    void shouldFindExistingWord() {
        BinarySearchStrategy searcher = new BinarySearchStrategy();
        var entries = Arrays.asList(
            new DictionaryEntry("apple", "apel"),
            new DictionaryEntry("cat", "kucing"),
            new DictionaryEntry("dog", "anjing")
        );

        SearchResult result = searcher.search(entries, "cat", Language.ENGLISH);
        
        assertTrue(result.getEntry().isPresent());
        assertEquals("kucing", result.getEntry().get().getIndonesian());
        assertFalse(result.getSteps().isEmpty());
    }

    @Test
    void shouldReturnEmptyForNonExistingWord() {
        BinarySearchStrategy searcher = new BinarySearchStrategy();
        var entries = Arrays.asList(
            new DictionaryEntry("apple", "apel"),
            new DictionaryEntry("cat", "kucing")
        );

        SearchResult result = searcher.search(entries, "zebra", Language.ENGLISH);
        
        assertFalse(result.getEntry().isPresent());
        assertFalse(result.getSteps().isEmpty());
    }
}