package com.coffeecode.core.repository.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.coffeecode.core.config.AppConfig;
import com.coffeecode.core.exception.DictionaryException;
import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.DictionaryWrapper;
import com.coffeecode.core.repository.DictionaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDictionaryRepository implements DictionaryRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonDictionaryRepository.class);
    private static final Marker PERFORMANCE = MarkerFactory.getMarker("PERFORMANCE");
    private final ObjectMapper objectMapper;

    public JsonDictionaryRepository() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<DictionaryEntry> loadEntries() {
        long startTime = System.currentTimeMillis();
        try {
            File file = new File(AppConfig.JSON_RESOURCE_PATH);
            logger.info("Loading dictionary from: {}", file.getAbsolutePath());
            validateFile(file);

            DictionaryWrapper wrapper = objectMapper.readValue(file, DictionaryWrapper.class);
            validateEntries(wrapper);

            long endTime = System.currentTimeMillis();
            logger.info(PERFORMANCE, "Dictionary loaded in {}ms. Total entries: {}",
                    (endTime - startTime), wrapper.getDictionary().size());

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