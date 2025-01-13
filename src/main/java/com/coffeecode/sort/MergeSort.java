package com.coffeecode.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.search.SearchType;

public class MergeSort implements Sortable {
    private static final Logger logger = LoggerFactory.getLogger(MergeSort.class);

    @Override
    public DictionaryEntry[] sort(DictionaryEntry[] dictionary, SearchType type) {
        logger.debug("Sorting dictionary by {}", type);
        if (dictionary == null || dictionary.length <= 1)
            return dictionary;

        DictionaryEntry[] temp = new DictionaryEntry[dictionary.length];
        mergeSort(dictionary, temp, 0, dictionary.length - 1, type);
        return dictionary;
    }

    private void mergeSort(DictionaryEntry[] arr, DictionaryEntry[] temp, int left, int right, SearchType type) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, temp, left, mid, type);
            mergeSort(arr, temp, mid + 1, right, type);
            merge(arr, temp, left, mid, right, type);
        }
    }

    private void merge(DictionaryEntry[] arr, DictionaryEntry[] temp, int left, int mid, int right, SearchType type) {
        System.arraycopy(arr, left, temp, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            String word1 = (type == SearchType.ENGLISH) ? temp[i].english() : temp[i].indonesian();
            String word2 = (type == SearchType.ENGLISH) ? temp[j].english() : temp[j].indonesian();

            if (word1.compareTo(word2) <= 0) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            arr[k] = temp[i];
            k++;
            i++;
        }
    }
}