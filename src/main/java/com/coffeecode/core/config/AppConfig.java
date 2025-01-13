package com.coffeecode.core.config;

public class AppConfig {
    private AppConfig() {
        throw new IllegalStateException("Configuration class: Dont Intantiete");
    }

    public static final String JSON_RESOURCE_PATH = "src/main/resources/data/wordsdictionary.json";
}