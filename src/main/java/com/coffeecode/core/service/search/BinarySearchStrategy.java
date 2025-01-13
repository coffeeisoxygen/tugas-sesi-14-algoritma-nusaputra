package com.coffeecode.core.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public class BinarySearchStrategy implements SearchStrategy {
    private static final Logger logger = LoggerFactory.getLogger(BinarySearchStrategy.class);
    private List<String> steps;

    @Override
    public SearchResult search(List<DictionaryEntry> entries, String word, Language language) {
        logger.debug("Starting binary search for '{}' in {} entries", word, entries.size());
        steps = new ArrayList<>();
        steps.add(String.format("Starting binary search for: %s", word));

        // Sort entries in a case-insensitive manner based on the specified language
        entries.sort((entry1, entry2) -> getWord(entry1, language).toLowerCase()
                .compareTo(getWord(entry2, language).toLowerCase()));

        int left = 0;
        int right = entries.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            DictionaryEntry midEntry = entries.get(mid);
            String midWord = getWord(midEntry, language);

            steps.add(String.format("Comparing with index %d: %s", mid, midWord));
            logger.trace("Comparing '{}' with '{}' at index {}", word, midWord, mid);

            // Case insensitive comparison
            int comparison = word.toLowerCase().compareTo(midWord.toLowerCase());

            if (comparison == 0) {
                steps.add("Word found!");
                logger.debug("Search completed. Word was found");
                return new SearchResult(Optional.of(midEntry), steps);
            }

            if (comparison > 0) {
                steps.add("Moving to right half");
                left = mid + 1;
            } else {
                steps.add("Moving to left half");
                right = mid - 1;
            }
        }

        steps.add("Word not found");
        logger.debug("Search completed. Word was not found");
        return new SearchResult(Optional.empty(), steps);
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
