package com.coffeecode.core.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.models.Language;
import com.coffeecode.core.service.search.SearchResult;

class BinarySearchDictionaryServiceTest {
    private BinarySearchDictionaryService service;

    @BeforeEach
    void setUp() {
        service = new BinarySearchDictionaryService();
        service.initialize();
    }

    @Test
    void shouldInitializeWithEnglishSorting() {
        assertFalse(service.getSortSteps().isEmpty());
    }

    @Test
    void shouldFindEnglishToIndonesian() {
        SearchResult result = service.searchWord("cat", Language.ENGLISH);
        
        assertTrue(result.getEntry().isPresent());
        assertEquals("kucing", result.getEntry().get().getIndonesian());
        assertFalse(result.getSteps().isEmpty());
    }

    @Test
    void shouldFindIndonesianToEnglish() {
        SearchResult result = service.searchWord("kucing", Language.INDONESIAN);
        
        assertTrue(result.getEntry().isPresent());
        assertEquals("cat", result.getEntry().get().getEnglish());
        assertFalse(result.getSteps().isEmpty());
    }

    @Test
    void shouldReturnSortSteps() {
        service.searchWord("cat", Language.ENGLISH);
        List<String> sortSteps = service.getSortSteps();
        
        assertFalse(sortSteps.isEmpty());
        assertTrue(sortSteps.get(0).contains("Dividing"));
    }

    @Test
    void shouldReturnSearchSteps() {
        service.searchWord("cat", Language.ENGLISH);
        List<String> searchSteps = service.getSearchSteps();
        
        assertFalse(searchSteps.isEmpty());
        assertTrue(searchSteps.get(0).contains("Starting binary search"));
    }
}