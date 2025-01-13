package com.coffeecode.core.service.sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

class MergeSortStrategyTest {
    private MergeSortStrategy sorter;
    private List<DictionaryEntry> entries;

    @BeforeEach
    void setUp() {
        sorter = new MergeSortStrategy();
        entries = Arrays.asList(
            new DictionaryEntry("cat", "kucing"),
            new DictionaryEntry("cats", "kucing"),
            new DictionaryEntry("apple", "apel"),
            new DictionaryEntry("dog", "anjing"),
            new DictionaryEntry("test", "tes")
        );
    }

    @Test
    void shouldSortByEnglishAlphabetically() {
        SortResult result = sorter.sort(entries, Language.ENGLISH);
        List<DictionaryEntry> sorted = result.getSortedEntries();
        
        assertEquals("apple", sorted.get(0).getEnglish());
        assertEquals("cat", sorted.get(1).getEnglish());
        assertEquals("cats", sorted.get(2).getEnglish());
        assertEquals("dog", sorted.get(3).getEnglish());
        assertEquals("test", sorted.get(4).getEnglish());
    }

    @Test
    void shouldSortByIndonesianAlphabetically() {
        SortResult result = sorter.sort(entries, Language.INDONESIAN);
        List<DictionaryEntry> sorted = result.getSortedEntries();
        
        assertEquals("anjing", sorted.get(0).getIndonesian());
        assertEquals("apel", sorted.get(1).getIndonesian());
        assertEquals("kucing", sorted.get(2).getIndonesian());
        assertEquals("kucing", sorted.get(3).getIndonesian());
        assertEquals("tes", sorted.get(4).getIndonesian());
    }

    @Test
    void shouldTrackSortingSteps() {
        SortResult result = sorter.sort(entries, Language.ENGLISH);
        List<String> steps = result.getSteps();
        
        assertFalse(steps.isEmpty());
        assertTrue(steps.stream().anyMatch(step -> step.contains("Dividing")));
        assertTrue(steps.stream().anyMatch(step -> step.contains("Comparing")));
    }

    @Test
    void shouldSortSameWordsByLength() {
        entries = Arrays.asList(
            new DictionaryEntry("test", "uji"),
            new DictionaryEntry("testing", "ujian"),
            new DictionaryEntry("test", "tes")
        );
        
        SortResult result = sorter.sort(entries, Language.ENGLISH);
        List<DictionaryEntry> sorted = result.getSortedEntries();
        
        assertEquals("test", sorted.get(0).getEnglish());
        assertEquals("test", sorted.get(1).getEnglish());
        assertEquals("testing", sorted.get(2).getEnglish());
    }
}