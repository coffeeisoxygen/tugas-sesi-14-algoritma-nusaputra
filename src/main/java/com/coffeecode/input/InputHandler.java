package com.coffeecode.input;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.coffeecode.search.SearchType;

public class InputHandler {
    private static final Logger logger = LoggerFactory.getLogger(InputHandler.class);
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getSearchTerm() {
        System.out.print("Enter word to search: ");
        return scanner.nextLine().trim();
    }

    public SearchType getSearchType() {
        while (true) {
            System.out.println("Select search type:");
            System.out.println("1. English to Indonesian");
            System.out.println("2. Indonesian to English");
            System.out.print("Choice (1/2): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    return SearchType.ENGLISH;
                }
                case "2" -> {
                    return SearchType.INDONESIAN;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void close() {
        scanner.close();
    }

    public String getCommand() {

        System.out.print("Enter command: ");
        return scanner.nextLine().trim();
    }
}