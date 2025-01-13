package com.coffeecode.core.service.search;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public interface SearchStrategy {
    SearchResult search(List<DictionaryEntry> entries, String word, Language language);
}
