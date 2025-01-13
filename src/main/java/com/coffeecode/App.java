package com.coffeecode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    public static void main(String[] args) {
        log.info("Starting application...");
        log.debug("Debug message");
        log.warn("Warning message");
        log.error("Error message");
        
        try {
            throw new RuntimeException("Test exception");
        } catch (Exception e) {
            log.error("Caught exception", e);
        }
    }
}
