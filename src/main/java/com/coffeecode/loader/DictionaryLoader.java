package com.coffeecode.loader;

import com.coffeecode.model.Word;

public interface DictionaryLoader {
    Word[] load(String source);
}