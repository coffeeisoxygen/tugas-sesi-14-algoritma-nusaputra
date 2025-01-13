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
import com.coffeecode.core.service.sort.MergeSortStrategy;
import com.coffeecode.core.service.sort.SortResult;

class DictionarySearchSimulationTest {
    private BinarySearchStrategy searcher;
    private List<DictionaryEntry> dictionary;
    private MergeSortStrategy sorter;

    @BeforeEach
    void setUp() {
        searcher = new BinarySearchStrategy();
        sorter = new MergeSortStrategy();
        dictionary = Arrays.asList(
            new DictionaryEntry("cat", "kucing"),
            new DictionaryEntry("dog", "anjing"),
            new DictionaryEntry("bird", "burung"),
            new DictionaryEntry("fish", "ikan"),
            new DictionaryEntry("rabbit", "kelinci")
        );
    }

    @Test
    void simulateEnglishToIndonesianSearch() {
        // First sort by English words
        SortResult sortResult = sorter.sort(dictionary, Language.ENGLISH);
        System.out.println("Sorting Steps:");
        sortResult.getSteps().forEach(System.out::println);

        // Then search for "dog"
        SearchResult searchResult = searcher.search(sortResult.getSortedEntries(), "dog", Language.ENGLISH);
        
        System.out.println("\nSearch Steps for 'dog':");
        searchResult.getSteps().forEach(System.out::println);
        
        assertTrue(searchResult.getEntry().isPresent());
        assertEquals("anjing", searchResult.getEntry().get().getIndonesian());
    }

    @Test
    void simulateIndonesianToEnglishSearch() {
        // First sort by Indonesian words
        SortResult sortResult = sorter.sort(dictionary, Language.INDONESIAN);
        System.out.println("Sorting Steps:");
        sortResult.getSteps().forEach(System.out::println);

        // Then search for "kucing"
        SearchResult searchResult = searcher.search(sortResult.getSortedEntries(), "kucing", Language.INDONESIAN);
        
        System.out.println("\nSearch Steps for 'kucing':");
        searchResult.getSteps().forEach(System.out::println);
        
        assertTrue(searchResult.getEntry().isPresent());
        assertEquals("cat", searchResult.getEntry().get().getEnglish());
    }

    @Test
    void simulateWordNotFound() {
        // Sort by English
        SortResult sortResult = sorter.sort(dictionary, Language.ENGLISH);
        
        // Search for non-existent word
        SearchResult searchResult = searcher.search(sortResult.getSortedEntries(), "elephant", Language.ENGLISH);
        
        System.out.println("\nSearch Steps for non-existent word 'elephant':");
        searchResult.getSteps().forEach(System.out::println);
        
        assertFalse(searchResult.getEntry().isPresent());
    }
}