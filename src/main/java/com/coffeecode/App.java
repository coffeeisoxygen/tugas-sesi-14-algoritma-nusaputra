package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.loader.JsonDictionaryLoader;
import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.core.repository.DictionaryRepository;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting Dictionary Application");

        try {
            // Initialize components
            DictionaryRepository repository = new DictionaryRepository(new JsonDictionaryLoader());

            // Load dictionary
            repository.initialize("/data/wordsdictionary.json");

            // Display results
            displayStatistics(repository);
            displaySampleEntries(repository.getDictionary());

        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage(), e);
        }
    }

    private static void displayStatistics(DictionaryRepository repository) {
        logger.info("Dictionary Statistics:");
        logger.info("Total entries: {}", repository.size());
        logger.info("----------------------------------------");
    }

    private static void displaySampleEntries(DictionaryEntry[] dictionary) {
        logger.info("Sample Dictionary Entries (First 5):");
        logger.info("----------------------------------------");

        for (int i = 0; i < Math.min(5, dictionary.length); i++) {
            DictionaryEntry entry = dictionary[i];
            logger.info(String.format("%3d. %-15s - %s",
                    i + 1,
                    entry.english(),
                    entry.indonesian()));
        }

        logger.info("----------------------------------------");
    }
}