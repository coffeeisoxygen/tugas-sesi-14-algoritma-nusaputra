package com.coffeecode.core.service.search;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

class BinarySearchStrategyTest {
    private BinarySearchStrategy searcher;
    private List<DictionaryEntry> sortedEntries;

    @BeforeEach
    void setUp() {
        searcher = new BinarySearchStrategy();
        sortedEntries = Arrays.asList(
            new DictionaryEntry("apple", "apel"),
            new DictionaryEntry("cat", "kucing"),
            new DictionaryEntry("dog", "anjing"),
            new DictionaryEntry("elephant", "gajah"),
            new DictionaryEntry("zebra", "zebra")
        );
        sortedEntries.sort((e1, e2) -> e1.getIndonesian().compareToIgnoreCase(e2.getIndonesian()));
        sortedEntries.sort((e1, e2) -> e1.getIndonesian().compareToIgnoreCase(e2.getIndonesian()));
    }

    @Test
    void shouldFindExistingWord() {
        SearchResult result = searcher.search(sortedEntries, "cat", Language.ENGLISH);
        
        assertTrue(result.getEntry().isPresent());
        assertEquals("kucing", result.getEntry().get().getIndonesian());
        assertTrue(result.getSteps().size() >= 2);
    }

    @Test
    void shouldReturnEmptyForNonExistingWord() {
        SearchResult result = searcher.search(sortedEntries, "bird", Language.ENGLISH);
        
        assertFalse(result.getEntry().isPresent());
        assertTrue(result.getSteps().size() >= 2);
    }

    @Test
    void shouldFindWordCaseInsensitive() {
        SearchResult result = searcher.search(sortedEntries, "CAT", Language.ENGLISH);
        
        assertTrue(result.getEntry().isPresent());
        assertEquals("kucing", result.getEntry().get().getIndonesian());
    }

    @Test
    void shouldTrackSearchSteps() {
        SearchResult result = searcher.search(sortedEntries, "elephant", Language.ENGLISH);
        List<String> steps = result.getSteps();
        
        assertTrue(steps.size() >= 3);
        assertTrue(steps.get(0).contains("Starting binary search"));
        assertTrue(steps.stream().anyMatch(step -> step.contains("Comparing")));
        assertTrue(steps.stream().anyMatch(step -> step.contains("found")));
    }

    @Test
    void shouldSearchInIndonesianLanguage() {
        SearchResult result = searcher.search(sortedEntries, "kucing", Language.INDONESIAN);
        
        assertTrue(result.getEntry().isPresent());
        assertEquals("cat", result.getEntry().get().getEnglish());
    }
}