package com.coffeecode.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.models.DictionaryEntri;
import com.coffeecode.core.models.Language;

public class DataPreparationService {
    private static final Logger logger = LoggerFactory.getLogger(DataPreparationService.class);
    private final VocabularyLoadable loader;
    private final MergeSort sorter;

    public DataPreparationService(VocabularyLoadable loader, Language sortLanguage) {
        this.loader = loader;
        this.sorter = new MergeSort(sortLanguage);
    }

    public List<DictionaryEntri> prepareData() {
        logger.info("Starting data preparation");
        List<DictionaryEntri> entries = loader.loadword();
        logger.info("Loaded {} entries, starting sort", entries.size());
        List<DictionaryEntri> sortedEntries = sorter.sortList(entries);
        logger.info("Data preparation completed");
        return sortedEntries;
    }
}