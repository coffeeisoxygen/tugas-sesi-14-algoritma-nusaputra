package com.coffeecode.core.models;

import java.util.List;

public class DictionaryWrapper {
    private List<DictionaryEntry> dictionary;

    public List<DictionaryEntry> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<DictionaryEntry> dictionary) {
        this.dictionary = dictionary;
    }
}
