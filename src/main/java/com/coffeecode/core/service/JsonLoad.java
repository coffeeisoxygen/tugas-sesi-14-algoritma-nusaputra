package com.coffeecode.core.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.models.DictionaryEntri;
import com.coffeecode.exception.CustomException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLoad implements VocabularyLoadable {
    private static final Logger logger = LoggerFactory.getLogger(JsonLoad.class);
    private static final String JSON_RESOURCE_PATH = "src/main/resources/data/wordsdictionary.json";

    @Override
    public List<DictionaryEntri> loadword() {
        logger.info("Starting to load dictionary from JSON");
        List<DictionaryEntri> entries = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, List<Map<String, String>>> dictionary = objectMapper.readValue(
                    new File(JSON_RESOURCE_PATH),
                    new TypeReference<>() {
                    });

            List<Map<String, String>> words = dictionary.get("dictionary");
            for (Map<String, String> word : words) {
                entries.add(new DictionaryEntri(
                        word.get("indonesian"),
                        word.get("english")));
            }
            logger.info("Successfully loaded {} dictionary entries", entries.size());

        } catch (IOException e) {
            logger.error("Error loading dictionary from JSON file at {}: {}", JSON_RESOURCE_PATH, e.getMessage(), e);
            throw new CustomException("Failed to load dictionary from JSON file at " + JSON_RESOURCE_PATH, e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new CustomException("An unexpected error occurred while loading dictionary", e);
        }

        return entries;
    }
}
