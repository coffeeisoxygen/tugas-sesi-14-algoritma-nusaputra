package com.coffeecode.core.service;

import java.util.List;

import com.coffeecode.core.models.Language;

public interface DictionaryService {
    String translate(String word, Language from, Language to);
    List<String> getSearchSteps(String word, Language from);
    List<String> getSortSteps();
}