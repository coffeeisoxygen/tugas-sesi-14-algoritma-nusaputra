package com.coffeecode.core.repository.interfaces;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;

public interface DictionaryRepository {
    List<DictionaryEntry> loadEntries();

    void saveEntries(List<DictionaryEntry> entries);
}
