package com.coffeecode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.search.SearchType;
import com.coffeecode.sort.Sortable;
import com.coffeecode.search.Searchable;
import com.coffeecode.exception.CustomException;

public class DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final Sortable sorter;
    private final Searchable searcher;
    private SearchType lastSortType;
    private DictionaryEntry[] sortedDictionary;

    public DictionaryService(Sortable sorter, Searchable searcher) {
        this.sorter = sorter;
        this.searcher = searcher;
        this.lastSortType = null;
    }

    public int searchWord(DictionaryEntry[] dictionary, String target, SearchType type) {
        if (dictionary == null || target == null || type == null) {
            throw new CustomException("Invalid search parameters");
        }

        logger.info("Processing search request for: {}", target);

        if (needsSort(dictionary, type)) {
            sortedDictionary = sorter.sort(dictionary.clone(), type);
            lastSortType = type;
            logger.debug("Dictionary sorted by {}", type);
        }

        return searcher.search(sortedDictionary, target, type);
    }

    private boolean needsSort(DictionaryEntry[] dictionary, SearchType type) {
        return sortedDictionary == null ||
                lastSortType != type ||
                dictionary.length != sortedDictionary.length;
    }
}
