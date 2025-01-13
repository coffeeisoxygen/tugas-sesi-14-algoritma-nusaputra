package com.coffeecode.core.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import com.coffeecode.core.loader.JsonDictionaryLoader;
import com.coffeecode.exception.CustomException;

class DictionaryRepositoryTest {
    private DictionaryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DictionaryRepository(new JsonDictionaryLoader());
    }

    @Test
    @DisplayName("Should initialize repository with valid data")
    void shouldInitializeWithValidData() {
        repository.initialize("/data/wordsdictionary.json");
        assertTrue(repository.size() > 0);
    }

    @Test
    @DisplayName("Should throw exception when accessing uninitialized dictionary")
    void shouldThrowExceptionWhenUninitialized() {
        assertThrows(CustomException.class, () -> repository.getDictionary());
    }
}