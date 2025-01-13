package com.coffeecode.core.model;

public record DictionaryEntry(String english, String indonesian) {
    public DictionaryEntry {
        if (english == null || english.trim().isEmpty()) {
            throw new IllegalArgumentException("English word cannot be empty");
        }
        if (indonesian == null || indonesian.trim().isEmpty()) {
            throw new IllegalArgumentException("Indonesian word cannot be empty");
        }
    }
}