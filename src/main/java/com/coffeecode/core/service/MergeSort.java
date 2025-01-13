package com.coffeecode.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.models.DictionaryEntri;
import com.coffeecode.core.models.Language;

public class MergeSort implements VocabularySortAble {
    private static final Logger logger = LoggerFactory.getLogger(MergeSort.class);
    private final Language sortLanguage;

    public MergeSort(Language sortLanguage) {
        this.sortLanguage = sortLanguage;
    }

    public List<DictionaryEntri> sortList(List<DictionaryEntri> list) {
        logger.info("Starting merge sort for {} entries", list.size());
        if (list.size() <= 1)
            return list;

        int mid = list.size() / 2;
        List<DictionaryEntri> left = new ArrayList<>(list.subList(0, mid));
        List<DictionaryEntri> right = new ArrayList<>(list.subList(mid, list.size()));

        left = sortList(left);
        right = sortList(right);

        return merge(left, right);
    }

    private List<DictionaryEntri> merge(List<DictionaryEntri> left, List<DictionaryEntri> right) {
        List<DictionaryEntri> result = new ArrayList<>();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            String leftWord = left.get(leftIndex).getTranslation(sortLanguage);
            String rightWord = right.get(rightIndex).getTranslation(sortLanguage);

            if (leftWord.compareToIgnoreCase(rightWord) <= 0) {
                result.add(left.get(leftIndex));
                leftIndex++;
            } else {
                result.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        result.addAll(left.subList(leftIndex, left.size()));
        result.addAll(right.subList(rightIndex, right.size()));

        return result;
    }

    @Override
    public List<DictionaryEntri> sort(List<DictionaryEntri> dictionaryEntris) {
        return sortList(dictionaryEntris);
    }
}
