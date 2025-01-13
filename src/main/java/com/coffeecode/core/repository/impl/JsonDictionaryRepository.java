package com.coffeecode.core.repository.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.config.AppConfig;
import com.coffeecode.core.exception.DictionaryException;
import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.DictionaryWrapper;
import com.coffeecode.core.repository.DictionaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDictionaryRepository implements DictionaryRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonDictionaryRepository.class);
    private final ObjectMapper objectMapper;

    public JsonDictionaryRepository() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<DictionaryEntry> loadEntries() {
        try {
            File file = new File(AppConfig.JSON_RESOURCE_PATH);
            validateFile(file);
            
            DictionaryWrapper wrapper = objectMapper.readValue(file, DictionaryWrapper.class);
            validateEntries(wrapper);
            
            logger.info("Successfully loaded {} dictionary entries", wrapper.getDictionary().size());
            return wrapper.getDictionary();
        } catch (Exception e) {
            logger.error("Failed to load dictionary", e);
            throw new DictionaryException(String.format(DictionaryException.LOADING_ERROR, e.getMessage()), e);
        }
    }

    private void validateFile(File file) {
        if (!file.exists()) {
            throw new DictionaryException("Dictionary file not found at: " + file.getAbsolutePath());
        }
        if (!file.canRead()) {
            throw new DictionaryException("Cannot read dictionary file at: " + file.getAbsolutePath());
        }
    }

    private void validateEntries(DictionaryWrapper wrapper) {
        if (wrapper == null || wrapper.getDictionary() == null || wrapper.getDictionary().isEmpty()) {
            throw new DictionaryException("Dictionary is empty or invalid");
        }
    }
}