package com.coffeecode.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Word;

class JsonDictionaryLoaderTest {
    private JsonDictionaryLoader loader;

    @BeforeEach
    void setUp() {
        loader = new JsonDictionaryLoader();
    }

    @Test
    void shouldLoadValidDictionaryFile() {
        Word[] words = loader.load("/data/wordsdictionary.json");
        assertNotNull(words);
        assertTrue(words.length > 0);
    }

    @Test
    void shouldReturnEmptyArrayForInvalidFile() {
        Word[] words = loader.load("/nonexistent.json");
        assertNotNull(words);
        assertEquals(0, words.length);
    }
}