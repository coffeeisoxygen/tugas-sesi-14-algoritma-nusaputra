package com.coffeecode.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DictionaryEntri {
    private String indonesian;
    private String english;

    public String getTranslation(Language language) {
        return language == Language.INDONESIAN ? indonesian : english;
    }

    @Override
    public String toString() {
        return "DictionaryEntri{" +
                "Indonesian='" + indonesian + '\'' +
                ", English='" + english + '\'' +
                '}';
    }
}
