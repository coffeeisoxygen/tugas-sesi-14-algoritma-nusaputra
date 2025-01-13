package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.core.loader.JsonDictionaryLoader;
import com.coffeecode.core.model.DictionaryEntry;
import com.coffeecode.core.repository.DictionaryRepository;
import com.coffeecode.input.InputHandler;
import com.coffeecode.search.SearchType;
import com.coffeecode.service.DictionaryService;
import com.coffeecode.search.BinarySearch;
import com.coffeecode.sort.MergeSort;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting Dictionary Application");
        InputHandler inputHandler = new InputHandler();

        try {
            // Initialize components
            DictionaryRepository repository = new DictionaryRepository(new JsonDictionaryLoader());
            DictionaryService service = new DictionaryService(new MergeSort(), new BinarySearch());

            // Load dictionary
            repository.initialize("/data/wordsdictionary.json");
            displayStatistics(repository);

            // Handle user input
            while (true) {
                displayMenu();
                String command = inputHandler.getCommand();
                
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                if (command.equalsIgnoreCase("search")) {
                    processSearch(inputHandler, service, repository);
                }
            }

        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage(), e);
        } finally {
            inputHandler.close();
        }
    }

    private static void displayMenu() {
        logger.info("\nAvailable commands:");
        logger.info("1. search - Search for a word");
        logger.info("2. exit   - Exit application");
        logger.info("----------------------------------------");
    }

    private static void processSearch(InputHandler inputHandler, DictionaryService service, DictionaryRepository repository) {
        String searchTerm = inputHandler.getSearchTerm();
        SearchType searchType = inputHandler.getSearchType();
        
        int result = service.searchWord(repository.getDictionary(), searchTerm, searchType);
        displaySearchResult(result, repository.getDictionary(), searchTerm, searchType);
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

    private static void displaySearchResult(int index, DictionaryEntry[] dictionary, String term, SearchType type) {
        if (index != -1) {
            DictionaryEntry entry = dictionary[index];
            logger.info("Found: {} - {}", entry.english(), entry.indonesian());
        } else {
            logger.info("Word '{}' not found", term);
        }
        logger.info("----------------------------------------");
    }
}