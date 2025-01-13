package com.coffeecode.core.service;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntri;

public interface VocabularyLoadable {

    // Load A raw Dictionary
    List<DictionaryEntri> loadword();

}
