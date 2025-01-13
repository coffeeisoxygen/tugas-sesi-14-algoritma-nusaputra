package com.coffeecode.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.search.BinarySearch;
import com.coffeecode.search.SearchType;
import com.coffeecode.sort.MergeSort;

class DictionaryServiceTest {
    private DictionaryService service;
    private DictionaryEntry[] dictionary;

    @BeforeEach
    void setUp() {
        service = new DictionaryService(new MergeSort(), new BinarySearch());
        dictionary = new DictionaryEntry[] {
                new DictionaryEntry("zebra", "zebra"),
                new DictionaryEntry("apple", "apel"),
                new DictionaryEntry("cat", "kucing")
        };
    }

    @Test
    void shouldFindWordAfterSorting() {
        int result = service.search(dictionary, "apple", SearchType.ENGLISH);
        assertEquals(0, result); // After sorting, "apple" should be first
    }

    @Test
    void shouldSortOnlyWhenSearchTypeChanges() {
        service.search(dictionary, "apple", SearchType.ENGLISH);
        int result = service.search(dictionary, "apel", SearchType.INDONESIAN);
        assertEquals(0, result); // Should sort again for Indonesian search
    }
}