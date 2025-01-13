package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        logger.debug("Debug message");
        logger.warn("Warning message");
        logger.error("Error message");

        try {
            throw new RuntimeException("Test exception");
        } catch (Exception e) {
            logger.error("Caught exception", e);
        }
    }
}
