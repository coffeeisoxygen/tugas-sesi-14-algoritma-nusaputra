package com.coffeecode.core.service.search;

import java.util.List;
import java.util.Optional;

import com.coffeecode.core.models.DictionaryEntry;

public class SearchResult {
    private final Optional<DictionaryEntry> entry;
    private final List<String> steps;

    public SearchResult(Optional<DictionaryEntry> entry, List<String> steps) {
        this.entry = entry;
        this.steps = steps;
    }

    public Optional<DictionaryEntry> getEntry() {
        return entry;
    }

    public List<String> getSteps() {
        return steps;
    }
}
