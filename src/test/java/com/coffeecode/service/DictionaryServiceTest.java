package com.coffeecode.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.search.BinarySearch;
import com.coffeecode.search.SearchType;
import com.coffeecode.sort.MergeSort;
import com.coffeecode.exception.CustomException;

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
    @DisplayName("Should find word after sorting English")
    void shouldFindWordAfterSortingEnglish() {
        int result = service.searchWord(dictionary, "apple", SearchType.ENGLISH);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should find word after sorting Indonesian")
    void shouldFindWordAfterSortingIndonesian() {
        int result = service.searchWord(dictionary, "apel", SearchType.INDONESIAN);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should maintain sort state")
    void shouldMaintainSortState() {
        service.searchWord(dictionary, "apple", SearchType.ENGLISH);
        int result = service.searchWord(dictionary, "apple", SearchType.ENGLISH);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should throw exception for null input")
    void shouldThrowExceptionForNullInput() {
        assertThrows(CustomException.class, () -> 
            service.searchWord(null, "test", SearchType.ENGLISH));
    }
}