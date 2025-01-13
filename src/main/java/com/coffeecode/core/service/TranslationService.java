package com.coffeecode.core.service;

import com.coffeecode.core.models.Language;

public interface TranslationService {
    String translate(String word, Language from, Language to);
    boolean isValidWord(String word, Language language);
}