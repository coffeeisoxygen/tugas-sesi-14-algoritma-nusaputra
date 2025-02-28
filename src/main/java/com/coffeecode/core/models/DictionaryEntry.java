package com.coffeecode.core.models;

public class DictionaryEntry {
    private String english;
    private String indonesian;

    public DictionaryEntry() {
    } // For Jackson

    public DictionaryEntry(String english, String indonesian) {
        this.english = english;
        this.indonesian = indonesian;
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

    public String getTranslation(Language language) {
        return language == Language.ENGLISH ? english : indonesian;
    }
}
