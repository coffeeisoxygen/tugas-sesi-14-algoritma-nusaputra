package com.coffeecode.sort;

import com.coffeecode.model.Word;
import com.coffeecode.search.SearchType;

public interface Sortable {
    Word[] sort(Word[] dictionary, SearchType type);
}