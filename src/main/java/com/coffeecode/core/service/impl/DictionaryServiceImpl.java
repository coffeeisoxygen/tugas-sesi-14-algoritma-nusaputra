package com.coffeecode.core.service.impl;

import com.coffeecode.core.service.interfaces.DictionaryService;
import com.coffeecode.core.models.Language;
import com.coffeecode.core.repository.interfaces.DictionaryRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.exception.CustomException;
import com.coffeecode.core.models.DictionaryEntry;

public class DictionaryServiceImpl implements DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);
    private final DictionaryRepository repository;

    public DictionaryServiceImpl(DictionaryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DictionaryEntry> getAllEntries() {
        return repository.loadEntries();
    }

    @Override
    public String translate(String word, Language from, Language to) {
        validateWord(word, from);
        // Implementation for translation
        return null;
    }

    @Override
    public void validateWord(String word, Language language) {
        if (word == null || word.trim().isEmpty()) {
            throw new CustomException("Word cannot be empty");
        }
    }

}
