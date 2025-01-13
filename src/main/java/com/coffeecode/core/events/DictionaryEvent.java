package com.coffeecode.core.events;

public record DictionaryEvent(
    DictionaryEventType type,
    String message,
    Object data
) {}
