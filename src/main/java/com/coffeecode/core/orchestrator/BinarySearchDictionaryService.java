package com.coffeecode.core.orchestrator;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;
import com.coffeecode.core.repository.DictionaryRepository;
import com.coffeecode.core.repository.impl.JsonDictionaryRepository;
import com.coffeecode.core.service.search.BinarySearchStrategy;
import com.coffeecode.core.service.search.SearchResult;
import com.coffeecode.core.service.search.SearchStrategy;
import com.coffeecode.core.service.sort.MergeSortStrategy;
import com.coffeecode.core.service.sort.SortResult;
import com.coffeecode.core.service.sort.SortingStrategy;

public class BinarySearchDictionaryService {
    private final DictionaryRepository repository;
    private final SearchStrategy searchStrategy;
    private final SortingStrategy sortStrategy;
    private List<DictionaryEntry> sortedEntries;
    private Language currentSortLanguage;
    private SearchResult lastSearchResult;
    private SortResult lastSortResult;

    public BinarySearchDictionaryService() {
        this.repository = new JsonDictionaryRepository();
        this.searchStrategy = new BinarySearchStrategy();
        this.sortStrategy = new MergeSortStrategy();
    }

    public void initialize() {
        List<DictionaryEntry> entries = repository.loadEntries();
        lastSortResult = sortStrategy.sort(entries, Language.ENGLISH);
        sortedEntries = lastSortResult.getSortedEntries();
        currentSortLanguage = Language.ENGLISH;
    }

    public SearchResult searchWord(String word, Language from) {
        if (currentSortLanguage != from) {
            lastSortResult = sortStrategy.sort(sortedEntries, from);
            sortedEntries = lastSortResult.getSortedEntries();
            currentSortLanguage = from;
        }
        lastSearchResult = searchStrategy.search(sortedEntries, word, from);
        return lastSearchResult;
    }

    public List<String> getSortSteps() {
        return lastSortResult != null ? lastSortResult.getSteps() : List.of();
    }

    public List<String> getSearchSteps() {
        return lastSearchResult != null ? lastSearchResult.getSteps() : List.of();
    }
}
