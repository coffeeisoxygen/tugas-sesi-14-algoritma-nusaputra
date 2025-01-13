package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class WordTest {
    @Test
    void shouldCreateWordWithValidInput() {
        assertDoesNotThrow(() -> new Word("apple", "apel"));
    }

    @Test
    void shouldThrowExceptionForNullEnglishWord() {
        assertThrows(IllegalArgumentException.class, () -> new Word(null, "apel"));
    }

    @Test
    void shouldThrowExceptionForEmptyIndonesianWord() {
        assertThrows(IllegalArgumentException.class, () -> new Word("apple", ""));
    }
}