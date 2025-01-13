package com.coffeecode.core.repository.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.config.AppConfig;
import com.coffeecode.core.exception.CustomException;
import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.DictionaryWrapper;
import com.coffeecode.core.repository.interfaces.DictionaryRepository;
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
            logger.info("Loading dictionary from: {}", file.getAbsolutePath());

            if (!file.exists()) {
                throw new CustomException("Dictionary file not found");
            }

            DictionaryWrapper wrapper = objectMapper.readValue(file, DictionaryWrapper.class);
            return wrapper.getDictionary();
        } catch (Exception e) {
            throw new CustomException("Failed to load dictionary", e);
        }
    }

    @Override
    public void saveEntries(List<DictionaryEntry> entries) {
        // Implementation for future use
    }
}