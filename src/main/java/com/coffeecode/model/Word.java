package com.coffeecode.model;

public class Word {
    private String english;
    private String indonesian;

    public Word() {
    }

    public Word(String english, String indonesian) {
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
}