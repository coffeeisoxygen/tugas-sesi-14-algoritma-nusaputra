package com.coffeecode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.search.SearchType;
import com.coffeecode.sort.Sortable;
import com.coffeecode.search.Searchable;

public class DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final Sortable sorter;
    private final Searchable searcher;
    private SearchType lastSortType;

    public DictionaryService(Sortable sorter, Searchable searcher) {
        this.sorter = sorter;
        this.searcher = searcher;
    }

    public int searchWord(DictionaryEntry[] dictionary, String target, SearchType type) {
        logger.info("Processing search request for: {}", target);

        if (lastSortType != type) {
            dictionary = sorter.sort(dictionary, type);
            lastSortType = type;
        }

        return searcher.search(dictionary, target, type);
    }
}
