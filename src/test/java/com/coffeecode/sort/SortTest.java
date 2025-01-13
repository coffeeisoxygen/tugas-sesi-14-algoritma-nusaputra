package com.coffeecode.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Word;
import com.coffeecode.search.SearchType;

class SortTest {
    private Sortable sorter;
    private Word[] unsortedArray;

    @BeforeEach
    void setUp() {
        sorter = new MergeSort();
        unsortedArray = new Word[] {
            new Word("zebra", "zebra"),
            new Word("apple", "apel"),
            new Word("mango", "mangga"),
            new Word("grape", "anggur")
        };
    }

    @Nested
    @DisplayName("English Word Sorting")
    class EnglishSortingTests {
        @Test
        @DisplayName("Should sort by English words")
        void shouldSortByEnglishWords() {
            Word[] sorted = sorter.sort(unsortedArray, SearchType.ENGLISH);
            assertEquals("apple", sorted[0].english());
            assertEquals("grape", sorted[1].english());
            assertEquals("mango", sorted[2].english());
            assertEquals("zebra", sorted[3].english());
        }
    }

    @Nested
    @DisplayName("Indonesian Word Sorting")
    class IndonesianSortingTests {
        @Test
        @DisplayName("Should sort by Indonesian words")
        void shouldSortByIndonesianWords() {
            Word[] sorted = sorter.sort(unsortedArray, SearchType.INDONESIAN);
            assertEquals("anggur", sorted[0].indonesian());
            assertEquals("apel", sorted[1].indonesian());
            assertEquals("mangga", sorted[2].indonesian());
            assertEquals("zebra", sorted[3].indonesian());
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {
        @Test
        @DisplayName("Should handle empty array")
        void shouldHandleEmptyArray() {
            Word[] empty = new Word[0];
            Word[] result = sorter.sort(empty, SearchType.ENGLISH);
            assertEquals(0, result.length);
        }

        @Test
        @DisplayName("Should handle null array")
        void shouldHandleNullArray() {
            Word[] result = sorter.sort(null, SearchType.ENGLISH);
            assertNull(result);
        }

        @Test
        @DisplayName("Should handle single element")
        void shouldHandleSingleElement() {
            Word[] single = new Word[] { new Word("test", "tes") };
            Word[] result = sorter.sort(single, SearchType.ENGLISH);
            assertEquals(1, result.length);
            assertEquals("test", result[0].english());
        }
    }
}
