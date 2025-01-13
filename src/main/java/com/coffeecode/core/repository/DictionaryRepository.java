package com.coffeecode.core.repository;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;

public interface DictionaryRepository {
    List<DictionaryEntry> loadEntries();
}
