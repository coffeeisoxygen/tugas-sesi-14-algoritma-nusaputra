package com.coffeecode.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.exception.CustomException;
import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;
import com.coffeecode.core.repository.DictionaryRepository;
import com.coffeecode.core.service.DictionaryService;
import com.coffeecode.core.service.search.SearchResult;
import com.coffeecode.core.service.search.SearchStrategy;
import com.coffeecode.core.service.sort.SortResult;
import com.coffeecode.core.service.sort.SortingStrategy;

public class DictionaryServiceImpl implements DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);
    private final DictionaryRepository repository;
    private final SearchStrategy searchStrategy;
    private final SortingStrategy sortStrategy;
    private List<DictionaryEntry> sortedEntries;
    private Language currentSortLanguage;

    public DictionaryServiceImpl(DictionaryRepository repository,
            SearchStrategy searchStrategy,
            SortingStrategy sortStrategy) {
        this.repository = repository;
        this.searchStrategy = searchStrategy;
        this.sortStrategy = sortStrategy;
    }

    @Override
    public List<DictionaryEntry> getAllEntries() {
        if (sortedEntries == null) {
            SortResult sortResult = sortStrategy.sort(repository.loadEntries(), Language.ENGLISH);
            sortedEntries = sortResult.getSortedEntries();
            currentSortLanguage = Language.ENGLISH;
        }
        return sortedEntries;
    }

    @Override
    public String findTranslation(String word, Language from, Language to) {
        if (currentSortLanguage != from) {
            SortResult sortResult = sortStrategy.sort(sortedEntries, from);
            sortedEntries = sortResult.getSortedEntries();
            currentSortLanguage = from;
        }

        SearchResult result = searchStrategy.search(sortedEntries, word, from);
        return result.getEntry()
                .map(entry -> from == Language.ENGLISH ? entry.getIndonesian() : entry.getEnglish())
                .orElseThrow(() -> new CustomException("Translation not found for: " + word));
    }

    @Override
    public List<String> getSearchSteps() {
        return searchStrategy.search(sortedEntries, "", currentSortLanguage).getSteps();
    }

}
