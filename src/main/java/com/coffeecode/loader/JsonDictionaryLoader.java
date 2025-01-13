package com.coffeecode.loader;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Word;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDictionaryLoader implements DictionaryLoader {
    private static final Logger logger = LoggerFactory.getLogger(JsonDictionaryLoader.class);
    private final ObjectMapper mapper;

    public JsonDictionaryLoader() {
        this.mapper = new ObjectMapper();
        logger.info("JsonDictionaryLoader initialized");
    }

    @Override
    public Word[] load(String source) {
        logger.info("Loading dictionary from: {}", source);
        try {
            InputStream is = getClass().getResourceAsStream(source);
            if (is == null) {
                logger.error("Resource not found: {}", source);
                return new Word[0];
            }
            JsonNode root = mapper.readTree(is);
            Word[] words = mapper.readValue(root.get("dictionary").toString(), Word[].class);
            logger.info("Successfully loaded {} words", words.length);
            return words;
        } catch (Exception e) {
            logger.error("Failed to load dictionary from {}: {}", source, e.getMessage());
            return new Word[0];
        }
    }
}