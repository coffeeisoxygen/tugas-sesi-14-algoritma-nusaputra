package com.coffeecode.repository;

import com.coffeecode.loader.DictionaryLoader;
import com.coffeecode.model.Word;

public class DictionaryRepository {
    private Word[] dictionary;
    private final DictionaryLoader loader;

    public DictionaryRepository(DictionaryLoader loader) {
        this.loader = loader;
    }

    public void initialize(String source) {
        this.dictionary = loader.load(source);
    }

    public Word[] getDictionary() {
        return dictionary;
    }

    public int size() {
        return dictionary != null ? dictionary.length : 0;
    }
}