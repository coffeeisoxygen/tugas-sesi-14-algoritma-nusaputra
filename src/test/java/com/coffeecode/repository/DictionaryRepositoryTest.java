package com.coffeecode.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.exception.CustomException;
import com.coffeecode.loader.JsonDictionaryLoader;

class DictionaryRepositoryTest {
    private DictionaryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DictionaryRepository(new JsonDictionaryLoader());
    }

    @Test
    @DisplayName("Should initialize repository with valid data")
    void shouldInitializeWithValidData() {
        assertDoesNotThrow(() -> repository.initialize("/data/wordsdictionary.json"));
        assertTrue(repository.size() > 0);
    }

    @Test
    @DisplayName("Should throw exception when accessing uninitialized dictionary")
    void shouldThrowExceptionWhenUninitialized() {
        assertThrows(CustomException.class, () -> repository.getDictionary());
    }

    @Test
    @DisplayName("Should throw exception for invalid source")
    void shouldThrowExceptionForInvalidSource() {
        assertThrows(CustomException.class, () -> repository.initialize("/invalid/path.json"));
    }
}