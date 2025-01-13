package com.coffeecode.core.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public class BinarySearchStrategy implements SearchStrategy {
    private List<String> steps;

    @Override
    public SearchResult search(List<DictionaryEntry> entries, String word, Language language) {
        steps = new ArrayList<>();
        steps.add(String.format("Starting binary search for: %s", word));

        int left = 0;
        int right = entries.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            DictionaryEntry midEntry = entries.get(mid);
            String midWord = language == Language.INDONESIAN ? midEntry.getIndonesian() : midEntry.getEnglish();

            steps.add(String.format("Comparing with index %d: %s", mid, midWord));

            int comparison = word.compareToIgnoreCase(midWord);

            if (comparison == 0) {
                steps.add("Word found!");
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
        return new SearchResult(Optional.empty(), steps);
    }
}
