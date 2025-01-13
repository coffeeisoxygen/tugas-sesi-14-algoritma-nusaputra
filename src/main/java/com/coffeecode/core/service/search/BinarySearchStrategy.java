package com.coffeecode.core.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public class BinarySearchStrategy implements SearchStrategy {
    private static final Logger logger = LoggerFactory.getLogger(BinarySearchStrategy.class);
    private static final Marker PERFORMANCE = MarkerFactory.getMarker("PERFORMANCE");
    private static final Marker OPERATION = MarkerFactory.getMarker("OPERATION");

    @Override
    public SearchResult search(List<DictionaryEntry> entries, String word, Language language) {
        long startTime = System.currentTimeMillis();
        int comparisons = 0;
        List<String> steps = new ArrayList<>();

        try {
            logger.info(OPERATION, "Starting binary search - Word: '{}', Language: {}, Entries: {}", 
                word, language, entries.size());
            steps.add(String.format("Starting binary search for: %s", word));

            entries.sort((entry1, entry2) -> getWord(entry1, language).toLowerCase()
                    .compareTo(getWord(entry2, language).toLowerCase()));
            logger.debug(OPERATION, "Entries sorted by {}", language);

            int left = 0;
            int right = entries.size() - 1;

            while (left <= right) {
                comparisons++;
                int mid = left + (right - left) / 2;
                DictionaryEntry midEntry = entries.get(mid);
                String midWord = getWord(midEntry, language);

                steps.add(String.format("Comparing with index %d: %s", mid, midWord));
                logger.debug(OPERATION, "Comparison #{} at index {} - '{}' with '{}'", 
                    comparisons, mid, word.toLowerCase(), midWord.toLowerCase());

                int comparison = word.toLowerCase().compareTo(midWord.toLowerCase());

                if (comparison == 0) {
                    logSuccess(startTime, comparisons, mid);
                    steps.add("Word found!");
                    return new SearchResult(Optional.of(midEntry), steps);
                }

                if (comparison > 0) {
                    steps.add("Moving to right half");
                    logger.debug(OPERATION, "Moving right: {} > {}", word, midWord);
                    left = mid + 1;
                } else {
                    steps.add("Moving to left half");
                    logger.debug(OPERATION, "Moving left: {} < {}", word, midWord);
                    right = mid - 1;
                }
            }

            logNotFound(startTime, comparisons, word);
            steps.add("Word not found");
            return new SearchResult(Optional.empty(), steps);

        } catch (Exception e) {
            logger.error("Search failed - Word: {}, Language: {}", word, language, e);
            throw e;
        }
    }

    private void logSuccess(long startTime, int comparisons, int foundIndex) {
        long duration = System.currentTimeMillis() - startTime;
        logger.info(PERFORMANCE, "Search successful - Duration: {}ms, Comparisons: {}", 
            duration, comparisons);
        logger.info(OPERATION, "Word found at index: {}", foundIndex);
    }

    private void logNotFound(long startTime, int comparisons, String word) {
        long duration = System.currentTimeMillis() - startTime;
        logger.info(PERFORMANCE, "Search completed - Duration: {}ms, Comparisons: {}", 
            duration, comparisons);
        logger.info(OPERATION, "Word not found: '{}'", word);
    }

    private String getWord(DictionaryEntry entry, Language language) {
        if (null == language) {
            throw new IllegalArgumentException("Unsupported language: " + language);
        } else
            switch (language) {
                case ENGLISH -> {
                    return entry.getEnglish();
                }
                case INDONESIAN -> {
                    return entry.getIndonesian();
                }
                default -> throw new IllegalArgumentException("Unsupported language: " + language);
            }
    }
}
