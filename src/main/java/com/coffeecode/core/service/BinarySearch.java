package com.coffeecode.core.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.models.DictionaryEntri;
import com.coffeecode.core.models.Language;
import com.coffeecode.exception.CustomException;

public class BinarySearch implements VocabularySearchable {
    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);
    private final List<DictionaryEntri> sortedEntries;

    public BinarySearch(List<DictionaryEntri> sortedEntries) {
        this.sortedEntries = sortedEntries;
    }

    @Override
    public String find(String word, Language language) {
        logger.info("Searching for word '{}' in {} language", word, language);
        return search(word, language)
                .map(entry -> entry
                        .getTranslation(language == Language.INDONESIAN ? Language.ENGLISH : Language.INDONESIAN))
                .orElseThrow(() -> new CustomException("Word not found: " + word));
    }

    private Optional<DictionaryEntri> search(String word, Language language) {
        int left = 0;
        int right = sortedEntries.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midWord = sortedEntries.get(mid).getTranslation(language);

            int comparison = word.compareToIgnoreCase(midWord);

            if (comparison == 0) {
                logger.info("Word found at index: {}", mid);
                return Optional.of(sortedEntries.get(mid));
            }

            if (comparison > 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        logger.info("Word not found: {}", word);
        return Optional.empty();
    }
}