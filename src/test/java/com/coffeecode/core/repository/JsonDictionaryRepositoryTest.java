package com.coffeecode.core.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.repository.impl.JsonDictionaryRepository;

class JsonDictionaryRepositoryTest {
    private final JsonDictionaryRepository repository = new JsonDictionaryRepository();

    @Test
    void shouldLoadEntriesFromJson() {
        List<DictionaryEntry> entries = repository.loadEntries();
        assertFalse(entries.isEmpty(), "Dictionary should not be empty");
        assertTrue(entries.size() >= 50, "Should have at least 50 entries");

        // Test first entry
        DictionaryEntry firstEntry = entries.get(0);
        assertEquals("apple", firstEntry.getEnglish());
        assertEquals("apel", firstEntry.getIndonesian());
    }
}