package com.coffeecode.core.models;

public class DictionaryEntry {
    private String english;
    private String indonesian;

    public DictionaryEntry() {
        // holder for Jackson
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getIndonesian() {
        return indonesian;
    }

    public void setIndonesian(String indonesian) {
        this.indonesian = indonesian;
    }
}
