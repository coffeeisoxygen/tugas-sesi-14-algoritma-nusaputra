package com.coffeecode.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.exception.DictionaryException;
import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;
import com.coffeecode.core.repository.DictionaryRepository;
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
    private SearchResult lastSearchResult;
    private SortResult lastSortResult;

    public DictionaryServiceImpl(DictionaryRepository repository,
            SearchStrategy searchStrategy,
            SortingStrategy sortStrategy) {
        this.repository = repository;
        this.searchStrategy = searchStrategy;
        this.sortStrategy = sortStrategy;
        initialize();
    }

    private void initialize() {
        List<DictionaryEntry> entries = repository.loadEntries();
        lastSortResult = sortStrategy.sort(entries, Language.ENGLISH);
        sortedEntries = lastSortResult.getSortedEntries();
        currentSortLanguage = Language.ENGLISH;
    }

    @Override
    public List<DictionaryEntry> getAllEntries() {
        return sortedEntries;
    }

    @Override
    public String findTranslation(String word, Language from, Language to) {
        if (currentSortLanguage != from) {
            lastSortResult = sortStrategy.sort(sortedEntries, from);
            sortedEntries = lastSortResult.getSortedEntries();
            currentSortLanguage = from;
        }

        lastSearchResult = searchStrategy.search(sortedEntries, word, from);
        return lastSearchResult.getEntry()
                .map(entry -> from == Language.ENGLISH ? entry.getIndonesian() : entry.getEnglish())
                .orElseThrow(() -> new DictionaryException(String.format(DictionaryException.WORD_NOT_FOUND, word)));
    }

    @Override
    public List<String> getSortingSteps() {
        return lastSortResult != null ? lastSortResult.getSteps() : List.of();
    }

    @Override
    public List<String> getSearchSteps() {
        return lastSearchResult != null ? lastSearchResult.getSteps() : List.of();
    }
}
