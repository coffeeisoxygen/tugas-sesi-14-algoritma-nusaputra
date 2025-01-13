package com.coffeecode.core.exception;

public class DictionaryException extends RuntimeException {
    public static final String WORD_NOT_FOUND = "Word not found: %s";
    public static final String INVALID_LANGUAGE = "Invalid language: %s";
    public static final String LOADING_ERROR = "Failed to load dictionary: %s";

    public DictionaryException(String message) {
        super(message);
    }

    public DictionaryException(String message, Throwable cause) {
        super(message, cause);
    }
}