package com.coffeecode.model;

public record Word(String english, String indonesian) {
    public Word {
        if (english == null || english.trim().isEmpty()) {
            throw new IllegalArgumentException("English word cannot be empty");
        }
        if (indonesian == null || indonesian.trim().isEmpty()) {
            throw new IllegalArgumentException("Indonesian word cannot be empty");
        }
    }
}