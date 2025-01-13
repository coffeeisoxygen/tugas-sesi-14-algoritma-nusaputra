package com.coffeecode.core.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class DictionaryEntryTest {
    
    @Test
    @DisplayName("Should create entry with valid input")
    void shouldCreateEntryWithValidInput() {
        assertDoesNotThrow(() -> new DictionaryEntry("apple", "apel"));
    }

    @Test
    @DisplayName("Should throw exception for null English word")
    void shouldThrowExceptionForNullEnglishWord() {
        assertThrows(IllegalArgumentException.class, 
            () -> new DictionaryEntry(null, "apel"));
    }

    @Test
    @DisplayName("Should throw exception for empty Indonesian word")
    void shouldThrowExceptionForEmptyIndonesianWord() {
        assertThrows(IllegalArgumentException.class, 
            () -> new DictionaryEntry("apple", ""));
    }
}