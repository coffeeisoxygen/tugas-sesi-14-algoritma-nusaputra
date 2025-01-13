package com.coffeecode.core.events;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.models.Language;

class DictionaryEventTest {
    private TestEventListener testListener;
    private List<DictionaryEvent> capturedEvents;

    private class TestEventListener implements DictionaryEventListener {
        @Override
        public void onDictionaryEvent(DictionaryEvent event) {
            capturedEvents.add(event);
        }
    }

    @BeforeEach
    void setUp() {
        capturedEvents = new ArrayList<>();
        testListener = new TestEventListener();
        DictionaryEventPublisher.subscribe(testListener);
    }

    @Test
    void shouldPublishAndReceiveDictionaryLoadedEvent() {
        DictionaryEvent event = new DictionaryEvent(
            DictionaryEventType.DICTIONARY_LOADED,
            "Dictionary loaded",
            52
        );
        
        DictionaryEventPublisher.publish(event);
        
        assertEquals(1, capturedEvents.size());
        assertEquals(DictionaryEventType.DICTIONARY_LOADED, capturedEvents.get(0).type());
        assertEquals(52, capturedEvents.get(0).data());
    }

    @Test
    void shouldPublishAndReceiveSearchEvents() {
        DictionaryEvent startEvent = new DictionaryEvent(
            DictionaryEventType.SEARCH_STARTED,
            "Searching for: test",
            Language.ENGLISH
        );
        
        DictionaryEventPublisher.publish(startEvent);
        
        assertEquals(1, capturedEvents.size());
        assertEquals(DictionaryEventType.SEARCH_STARTED, capturedEvents.get(0).type());
        assertEquals("Searching for: test", capturedEvents.get(0).message());
    }

    @Test
    void shouldUnsubscribeListener() {
        DictionaryEventPublisher.unsubscribe(testListener);
        
        DictionaryEvent event = new DictionaryEvent(
            DictionaryEventType.SEARCH_COMPLETED,
            "Search completed",
            null
        );
        
        DictionaryEventPublisher.publish(event);
        
        assertTrue(capturedEvents.isEmpty());
    }
}