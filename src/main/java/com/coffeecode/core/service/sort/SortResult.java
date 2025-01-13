package com.coffeecode.core.service.sort;

import java.util.List;

import com.coffeecode.core.models.DictionaryEntry;

public class SortResult {
    private final List<DictionaryEntry> sortedEntries;
    private final List<String> steps;

    public SortResult(List<DictionaryEntry> sortedEntries, List<String> steps) {
        this.sortedEntries = sortedEntries;
        this.steps = steps;
    }

    public List<DictionaryEntry> getSortedEntries() { return sortedEntries; }
    public List<String> getSteps() { return steps; }
}