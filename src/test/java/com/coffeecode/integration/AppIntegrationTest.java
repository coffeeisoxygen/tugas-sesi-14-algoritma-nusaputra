package com.coffeecode.integration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.coffeecode.core.repository.DictionaryRepository;
import com.coffeecode.core.loader.JsonDictionaryLoader;
import com.coffeecode.service.DictionaryService;
import com.coffeecode.search.BinarySearch;
import com.coffeecode.sort.MergeSort;
import com.coffeecode.search.SearchType;

class AppIntegrationTest {
    private DictionaryRepository repository;
    private DictionaryService service;

    @BeforeEach
    void setUp() {
        repository = new DictionaryRepository(new JsonDictionaryLoader());
        service = new DictionaryService(new MergeSort(), new BinarySearch());
        repository.initialize("/data/wordsdictionary.json");
    }

    @Test
    @DisplayName("Should find English word")
    void shouldFindEnglishWord() {
        int result = service.searchWord(repository.getDictionary(), "apple", SearchType.ENGLISH);
        assertNotEquals(-1, result);
    }

    @Test
    @DisplayName("Should find Indonesian word")
    void shouldFindIndonesianWord() {
        int result = service.searchWord(repository.getDictionary(), "apel", SearchType.INDONESIAN);
        assertNotEquals(-1, result);
    }

    @Test
    @DisplayName("Should handle not found word")
    void shouldHandleNotFoundWord() {
        int result = service.searchWord(repository.getDictionary(), "notexist", SearchType.ENGLISH);
        assertEquals(-1, result);
    }
}