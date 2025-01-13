package com.coffeecode.search;

import com.coffeecode.model.Word;

public interface Searchable {
    /**
     * Search for a word in the dictionary
     * 
     * @param dictionary Array of words to search in
     * @param target     Word to search for
     * @param type       Type of search (ENGLISH or INDONESIAN)
     * @return index of found word, -1 if not found
     */
    int search(Word[] dictionary, String target, SearchType type);
}
