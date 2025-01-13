package com.coffeecode.core.loader;

import com.coffeecode.core.model.DictionaryEntry;

public interface DictionaryLoader {
    DictionaryEntry[] load(String source);
}