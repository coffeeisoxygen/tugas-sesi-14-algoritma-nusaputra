package com.coffeecode.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Word;

public class BinarySearch implements Searchable {
    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);

    @Override
    public int search(Word[] dictionary, String target, SearchType type) {
        logger.debug("Performing binary search for '{}' using {}", target, type);

        int left = 0;
        int right = dictionary.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String currentWord = (type == SearchType.ENGLISH)
                    ? dictionary[mid].english()
                    : dictionary[mid].indonesian();

            int comparison = currentWord.compareTo(target);

            if (comparison == 0) {
                logger.debug("Found '{}' at index {}", target, mid);
                return mid;
            }

            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        logger.debug("'{}' not found in dictionary", target);
        return -1;
    }
}