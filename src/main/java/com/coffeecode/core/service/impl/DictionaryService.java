package com.coffeecode.core.service.impl;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public interface DictionaryService {
    List<DictionaryEntry> getAllEntries();

    String findTranslation(String word, Language from, Language to);

    List<String> getSortingSteps();

    List<String> getSearchSteps();
}
