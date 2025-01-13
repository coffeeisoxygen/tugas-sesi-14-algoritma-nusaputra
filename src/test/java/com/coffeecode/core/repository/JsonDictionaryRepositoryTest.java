package com.coffeecode.core.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.repository.impl.JsonDictionaryRepository;

class JsonDictionaryRepositoryTest {
    private final DictionaryRepository repository = new JsonDictionaryRepository();

    @Test
    void shouldLoadDictionaryEntries() {
        List<DictionaryEntry> entries = repository.loadEntries();
        assertFalse(entries.isEmpty(), "Dictionary should not be empty");

        DictionaryEntry firstEntry = entries.get(0);
        assertNotNull(firstEntry.getEnglish());
        assertNotNull(firstEntry.getIndonesian());
    }
}