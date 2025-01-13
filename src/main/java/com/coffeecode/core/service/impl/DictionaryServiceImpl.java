package com.coffeecode.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;
import com.coffeecode.core.repository.DictionaryRepository;
import com.coffeecode.core.service.DictionaryService;

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
    public String findTranslation(String word, Language from, Language to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findTranslation'");
    }

    @Override
    public List<String> getSearchSteps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSearchSteps'");
    }

}
