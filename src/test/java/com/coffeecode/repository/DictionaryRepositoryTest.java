package com.coffeecode.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.loader.JsonDictionaryLoader;

class DictionaryRepositoryTest {
    private DictionaryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DictionaryRepository(new JsonDictionaryLoader());
    }

    @Test
    void shouldInitializeRepository() {
        repository.initialize("/data/wordsdictionary.json");
        assertTrue(repository.size() > 0);
    }

    @Test
    void shouldReturnZeroSizeForUninitializedRepository() {
        assertEquals(0, repository.size());
    }
}