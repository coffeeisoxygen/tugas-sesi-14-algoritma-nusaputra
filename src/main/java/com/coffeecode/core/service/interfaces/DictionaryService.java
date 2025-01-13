package com.coffeecode.core.service.interfaces;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public interface DictionaryService {
    List<DictionaryEntry> getAllEntries();

    String translate(String word, Language from, Language to);

    void validateWord(String word, Language language);
}
