package com.coffeecode.core.service;

import com.coffeecode.core.models.Language;

public interface VocabularySearchable {

    String find(String word, Language language);

}
