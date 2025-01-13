package com.coffeecode.sort;

import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.search.SearchType;

public interface Sortable {
    DictionaryEntry[] sort(DictionaryEntry[] dictionary, SearchType type);
}