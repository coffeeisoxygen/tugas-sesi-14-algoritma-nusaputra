package com.coffeecode.core.loader;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.exception.CustomException;
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
    public DictionaryEntry[] load(String source) throws CustomException {
        logger.info("Loading dictionary from: {}", source);
        try {
            InputStream is = getClass().getResourceAsStream(source);
            if (is == null) {
                logger.error("Resource not found: {}", source);
                return new DictionaryEntry[0];
            }
            JsonNode root = mapper.readTree(is);
            DictionaryEntry[] words = mapper.readValue(root.get("dictionary").toString(), DictionaryEntry[].class);
            logger.info("Successfully loaded {} words", words.length);
            return words;
        } catch (IOException e) {
            logger.error("IO error while loading dictionary from {}: {}", source, e.getMessage());
            return new DictionaryEntry[0];
        } catch (Exception e) {
            logger.error("Unexpected error while loading dictionary from {}: {}", source, e.getMessage());
            return new DictionaryEntry[0];
        }
    }
}