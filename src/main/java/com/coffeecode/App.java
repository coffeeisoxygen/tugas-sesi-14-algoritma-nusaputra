package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.loader.JsonDictionaryLoader;
import com.coffeecode.model.Word;
import com.coffeecode.repository.DictionaryRepository;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting Dictionary Application");

        try {
            // Initialize repository
            DictionaryRepository repository = new DictionaryRepository(new JsonDictionaryLoader());
            repository.initialize("/data/wordsdictionary.json");

            // Display dictionary statistics
            displayStatistics(repository);

            // Display sample entries
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

    private static void displaySampleEntries(Word[] dictionary) {
        logger.info("Sample Dictionary Entries (First 5):");
        logger.info("----------------------------------------");
        logger.info("  #  English         Indonesian");
        logger.info("----------------------------------------");
        
        for (int i = 0; i < Math.min(5, dictionary.length); i++) {
            Word word = dictionary[i];
            logger.info(String.format("%3d. %-15s - %s", 
                    i + 1,
                    word.english(),
                    word.indonesian()));
        }
        
        logger.info("----------------------------------------");
        logger.info("Showing {} of {} entries", 
                Math.min(5, dictionary.length), 
                dictionary.length);
    }
}
