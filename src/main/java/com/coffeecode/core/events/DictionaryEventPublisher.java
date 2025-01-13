package com.coffeecode.core.events;

import java.util.ArrayList;
import java.util.List;

public class DictionaryEventPublisher {
    private static final List<DictionaryEventListener> listeners = new ArrayList<>();
    
    public static void subscribe(DictionaryEventListener listener) {
        listeners.add(listener);
    }
    
    public static void unsubscribe(DictionaryEventListener listener) {
        listeners.remove(listener);
    }
    
    public static void publish(DictionaryEvent event) {
        listeners.forEach(listener -> listener.onDictionaryEvent(event));
    }
}