package com.coffeecode.core.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.loader.DictionaryLoader;
import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.exception.CustomException;

public class DictionaryRepository {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryRepository.class);
    private DictionaryEntry[] dictionary;
    private final DictionaryLoader loader;
    private boolean isInitialized;

    public DictionaryRepository(DictionaryLoader loader) {
        this.loader = loader;
        this.isInitialized = false;
        logger.debug("DictionaryRepository created with loader: {}", loader.getClass().getSimpleName());
    }

    public void initialize(String source) {
        logger.info("Initializing dictionary from source: {}", source);
        this.dictionary = loader.load(source);

        if (dictionary == null || dictionary.length == 0) {
            logger.error("Failed to initialize dictionary - no data loaded");
            throw new CustomException("Dictionary initialization failed");
        }

        this.isInitialized = true;
        logger.info("Dictionary initialized with {} words", dictionary.length);
    }

    public DictionaryEntry[] getDictionary() {
        validateInitialization();
        return dictionary;
    }

    public int size() {
        validateInitialization();
        return dictionary.length;
    }

    private void validateInitialization() {
        if (!isInitialized) {
            logger.error("Attempted to access uninitialized dictionary");
            throw new CustomException("Dictionary not initialized");
        }
    }
}