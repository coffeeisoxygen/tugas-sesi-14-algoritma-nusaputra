package com.coffeecode.core.service.sort;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public class MergeSortStrategy implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(MergeSortStrategy.class);
    private List<String> steps;
    private Language sortBy;

    @Override
    public SortResult sort(List<DictionaryEntry> entries, Language sortBy) {
        logger.info("Starting merge sort for {} entries by {}", entries.size(), sortBy);
        this.steps = new ArrayList<>();
        this.sortBy = sortBy;

        List<DictionaryEntry> result = new ArrayList<>(entries);
        mergeSort(result, 0, result.size() - 1);
        logger.info("Sort completed");

        return new SortResult(result, steps);
    }

    private void mergeSort(List<DictionaryEntry> entries, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            steps.add(String.format("Dividing array at index %d", mid));

            mergeSort(entries, left, mid);
            mergeSort(entries, mid + 1, right);

            merge(entries, left, mid, right);
        }
    }

    private void merge(List<DictionaryEntry> entries, int left, int mid, int right) {
        logger.trace("Merging subarrays: left={}, mid={}, right={}", left, mid, right);
        List<DictionaryEntry> leftArr = new ArrayList<>(entries.subList(left, mid + 1));
        List<DictionaryEntry> rightArr = new ArrayList<>(entries.subList(mid + 1, right + 1));

        int i = 0;
        int j = 0; 
        int k = left;

        while (i < leftArr.size() && j < rightArr.size()) {
            DictionaryEntry leftEntry = leftArr.get(i);
            DictionaryEntry rightEntry = rightArr.get(j);

            String leftWord = getWord(leftEntry);
            String rightWord = getWord(rightEntry);

            steps.add(String.format("Comparing '%s' with '%s'", leftWord, rightWord));

            if (compare(leftWord, rightWord) <= 0) {
                entries.set(k++, leftArr.get(i++));
            } else {
                entries.set(k++, rightArr.get(j++));
            }
        }

        while (i < leftArr.size())
            entries.set(k++, leftArr.get(i++));
        while (j < rightArr.size())
            entries.set(k++, rightArr.get(j++));
    }

    private String getWord(DictionaryEntry entry) {
        return sortBy == Language.INDONESIAN ? entry.getIndonesian() : entry.getEnglish();
    }

    private int compare(String word1, String word2) {
        int alphabeticalCompare = word1.compareToIgnoreCase(word2);
        return alphabeticalCompare != 0 ? alphabeticalCompare
                : Integer.compare(word1.length(), word2.length());
    }
}