package com.coffeecode.core.service.sort;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;

public class MergeSortStrategy implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(MergeSortStrategy.class);
    private static final Marker PERFORMANCE = MarkerFactory.getMarker("PERFORMANCE");
    private static final Marker OPERATION = MarkerFactory.getMarker("OPERATION");
    
    private Language sortBy;
    private List<String> steps;
    private int comparisons;
    private int mergeOperations;

    @Override
    public SortResult sort(List<DictionaryEntry> entries, Language sortBy) {
        long startTime = System.currentTimeMillis();
        this.sortBy = sortBy;
        this.steps = new ArrayList<>();
        this.comparisons = 0;
        this.mergeOperations = 0;

        logger.info(OPERATION, "Starting merge sort - Entries: {}, Language: {}", entries.size(), sortBy);
        List<DictionaryEntry> result = mergeSort(new ArrayList<>(entries), 0, entries.size() - 1);
        
        logPerformanceMetrics(startTime);
        return new SortResult(result, steps);
    }

    private List<DictionaryEntry> mergeSort(List<DictionaryEntry> entries, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            logger.debug(OPERATION, "Dividing array at index {}", mid);
            steps.add(String.format("Dividing array at index %d", mid));
            
            mergeSort(entries, left, mid);
            mergeSort(entries, mid + 1, right);
            merge(entries, left, mid, right);
            mergeOperations++;
        }
        return entries;
    }

    private void merge(List<DictionaryEntry> entries, int left, int mid, int right) {
        logger.debug(OPERATION, "Merging subarrays: left={}, mid={}, right={}", left, mid, right);
        
        List<DictionaryEntry> leftArr = new ArrayList<>(entries.subList(left, mid + 1));
        List<DictionaryEntry> rightArr = new ArrayList<>(entries.subList(mid + 1, right + 1));

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftArr.size() && j < rightArr.size()) {
            comparisons++;
            DictionaryEntry leftEntry = leftArr.get(i);
            DictionaryEntry rightEntry = rightArr.get(j);

            String leftWord = getWord(leftEntry);
            String rightWord = getWord(rightEntry);

            logger.trace(OPERATION, "Comparing '{}' with '{}'", leftWord, rightWord);
            steps.add(String.format("Comparing '%s' with '%s'", leftWord, rightWord));

            if (compare(leftWord, rightWord) <= 0) {
                entries.set(k++, leftArr.get(i++));
            } else {
                entries.set(k++, rightArr.get(j++));
            }
        }

        while (i < leftArr.size()) entries.set(k++, leftArr.get(i++));
        while (j < rightArr.size()) entries.set(k++, rightArr.get(j++));
    }

    private void logPerformanceMetrics(long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        logger.info(PERFORMANCE, "Sort completed - Duration: {}ms, Comparisons: {}, Merges: {}", 
            duration, comparisons, mergeOperations);
        logger.info(OPERATION, "Sort finished with {} comparisons and {} merge operations", 
            comparisons, mergeOperations);
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