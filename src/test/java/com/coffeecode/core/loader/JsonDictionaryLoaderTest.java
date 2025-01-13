package com.coffeecode.core.loader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.coffeecode.core.model.DictionaryEntry;

class JsonDictionaryLoaderTest {
    private JsonDictionaryLoader loader;

    @BeforeEach
    void setUp() {
        loader = new JsonDictionaryLoader();
    }

    @Test
    @DisplayName("Should load dictionary from valid JSON")
    void shouldLoadFromValidJson() {
        DictionaryEntry[] entries = loader.load("/data/wordsdictionary.json");
        assertNotNull(entries);
        assertTrue(entries.length > 0);
    }

    @Test
    @DisplayName("Should return empty array for invalid path")
    void shouldReturnEmptyArrayForInvalidPath() {
        DictionaryEntry[] entries = loader.load("/invalid/path.json");
        assertNotNull(entries);
        assertEquals(0, entries.length);
    }
}