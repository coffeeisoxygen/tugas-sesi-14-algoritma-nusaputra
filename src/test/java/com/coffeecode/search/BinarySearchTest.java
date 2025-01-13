package com.coffeecode.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Word;

class BinarySearchTest {
    private BinarySearch binarySearch;
    private Word[] sortedDictionary;
    private Word[] unsortedDictionary;

    @BeforeEach
    void setUp() {
        binarySearch = new BinarySearch();
        
        // Sorted array
        sortedDictionary = new Word[] {
            new Word("apple", "apel"),
            new Word("banana", "pisang"),
            new Word("grape", "anggur"),
            new Word("orange", "jeruk"),
            new Word("watermelon", "semangka")
        };

        // Unsorted array
        unsortedDictionary = new Word[] {
            new Word("grape", "anggur"),
            new Word("watermelon", "semangka"),
            new Word("apple", "apel"),
            new Word("orange", "jeruk"),
            new Word("banana", "pisang")
        };
    }

    @Nested
    @DisplayName("Search in Sorted Array")
    class SortedArrayTests {
        @Test
        @DisplayName("Should find existing word")
        void shouldFindExistingWord() {
            assertEquals(2, binarySearch.search(sortedDictionary, "grape", SearchType.ENGLISH));
            assertEquals(3, binarySearch.search(sortedDictionary, "jeruk", SearchType.INDONESIAN));
        }

        @Test
        @DisplayName("Should find boundary elements")
        void shouldFindBoundaryElements() {
            assertEquals(0, binarySearch.search(sortedDictionary, "apple", SearchType.ENGLISH));
            assertEquals(4, binarySearch.search(sortedDictionary, "watermelon", SearchType.ENGLISH));
        }
    }

    @Nested
    @DisplayName("Search in Unsorted Array")
    class UnsortedArrayTests {
        @Test
        @DisplayName("Should not find word in unsorted array")
        void shouldNotFindWordInUnsortedArray() {
            assertNotEquals(2, binarySearch.search(unsortedDictionary, "grape", SearchType.ENGLISH));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {
        @Test
        @DisplayName("Should handle empty array")
        void shouldHandleEmptyArray() {
            assertEquals(-1, binarySearch.search(new Word[0], "test", SearchType.ENGLISH));
        }

        @Test
        @DisplayName("Should handle null array")
        void shouldHandleNullArray() {
            assertEquals(-1, binarySearch.search(null, "test", SearchType.ENGLISH));
        }

        @Test
        @DisplayName("Should handle null search term")
        void shouldHandleNullSearchTerm() {
            assertEquals(-1, binarySearch.search(sortedDictionary, null, SearchType.ENGLISH));
        }
    }
}
