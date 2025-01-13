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
    }

    @Override
    public Word[] load(String source) {
        try {
            InputStream is = getClass().getResourceAsStream(source);
            JsonNode root = mapper.readTree(is);
            return mapper.readValue(root.get("dictionary").toString(), Word[].class);
        } catch (Exception e) {
            logger.error("Failed to load dictionary from {}", source, e);
            return new Word[0];
        }
    }
}