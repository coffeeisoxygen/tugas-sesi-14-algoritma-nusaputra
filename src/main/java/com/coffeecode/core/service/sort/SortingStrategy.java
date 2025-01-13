package com.coffeecode.core.service.sort;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public interface SortingStrategy {
    SortResult sort(List<DictionaryEntry> entries, Language sortBy);
}
