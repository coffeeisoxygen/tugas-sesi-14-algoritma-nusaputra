package com.coffeecode.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

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
import com.coffeecode.core.events.DictionaryEvent;
import com.coffeecode.core.events.DictionaryEventPublisher;
import com.coffeecode.core.events.DictionaryEventType;

public class BinarySearchDictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(BinarySearchDictionaryService.class);
    private static final Marker PERFORMANCE = MarkerFactory.getMarker("PERFORMANCE");
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
        DictionaryEventPublisher.publish(new DictionaryEvent(
            DictionaryEventType.DICTIONARY_LOADED,
            "Loading dictionary...",
            null
        ));

        long startTime = System.currentTimeMillis();
        logger.info("Starting dictionary initialization");
        
        try {
            List<DictionaryEntry> entries = repository.loadEntries();
            logger.debug("Loaded {} dictionary entries", entries.size());
            
            lastSortResult = sortStrategy.sort(entries, Language.ENGLISH);
            sortedEntries = lastSortResult.getSortedEntries();
            currentSortLanguage = Language.ENGLISH;
            
            long endTime = System.currentTimeMillis();
            logger.info(PERFORMANCE, "Initialization completed in {}ms", (endTime - startTime));
        } catch (Exception e) {
            logger.error("Failed to initialize dictionary", e);
            throw e;
        }

        DictionaryEventPublisher.publish(new DictionaryEvent(
            DictionaryEventType.DICTIONARY_LOADED,
            "Dictionary loaded successfully",
            sortedEntries.size()
        ));
    }

    public SearchResult searchWord(String word, Language from) {
        DictionaryEventPublisher.publish(new DictionaryEvent(
            DictionaryEventType.SEARCH_STARTED,
            "Starting search for: " + word,
            from
        ));

        long startTime = System.currentTimeMillis();
        logger.info("Search request: word='{}', language={}", word, from);
        
        try {
            if (currentSortLanguage != from) {
                logger.info(PERFORMANCE, "Resorting dictionary for {} search", from);
                long sortStart = System.currentTimeMillis();
                lastSortResult = sortStrategy.sort(sortedEntries, from);
                sortedEntries = lastSortResult.getSortedEntries();
                currentSortLanguage = from;
                logger.info(PERFORMANCE, "Resort completed in {}ms", System.currentTimeMillis() - sortStart);
            }

            lastSearchResult = searchStrategy.search(sortedEntries, word, from);
            
            long endTime = System.currentTimeMillis();
            logger.info(PERFORMANCE, "Total search operation completed in {}ms", (endTime - startTime));
            
            DictionaryEventPublisher.publish(new DictionaryEvent(
                DictionaryEventType.SEARCH_COMPLETED,
                "Search completed",
                lastSearchResult
            ));
            
            return lastSearchResult;
        } catch (Exception e) {
            logger.error("Search failed", e);
            throw e;
        }
    }

    public List<String> getSortSteps() {
        return lastSortResult != null ? lastSortResult.getSteps() : List.of();
    }

    public List<String> getSearchSteps() {
        return lastSearchResult != null ? lastSearchResult.getSteps() : List.of();
    }
}
