package com.coffeecode.core.repository.impl;

import java.io.File;
import java.io.IOException;
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
            if (!file.exists()) {
                throw new DictionaryException("Dictionary file not found");
            }

            DictionaryWrapper wrapper = objectMapper.readValue(file, DictionaryWrapper.class);
            if (wrapper.getDictionary() == null || wrapper.getDictionary().isEmpty()) {
                throw new DictionaryException("Dictionary is empty");
            }

            return wrapper.getDictionary();
        } catch (IOException e) {
            throw new DictionaryException(DictionaryException.LOADING_ERROR, e);
        }
    }

}