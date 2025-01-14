package com.coffeecode.ui;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.events.DictionaryEvent;
import com.coffeecode.core.events.DictionaryEventType;
import com.coffeecode.core.models.DictionaryEntry;
import com.coffeecode.core.models.Language;
import com.coffeecode.core.service.search.SearchResult;

class SearchFunctionalityTest {
    private MainUI mainUI;
    private JTextField searchField;
    private JComboBox<Language> languageCombo;
    private JButton searchButton;
    private JLabel resultLabel;
    private JTextArea stepsArea;

    @BeforeEach
    void setUp() {
        SwingUtilities.invokeLater(() -> {
            mainUI = new MainUI();
            // Get components using reflection for testing
            searchField = (JTextField) TestUtils.getField(mainUI, "searchField");
            languageCombo = (JComboBox) TestUtils.getField(mainUI, "languageCombo");
            searchButton = (JButton) TestUtils.getField(mainUI, "searchButton");
            resultLabel = (JLabel) TestUtils.getField(mainUI, "resultLabel");
            stepsArea = (JTextArea) TestUtils.getField(mainUI, "stepsArea");
        });
    }

    @Test
    void shouldHandleEmptySearchInput() {
        SwingUtilities.invokeLater(() -> {
            searchField.setText("");
            searchButton.doClick();
            assertEquals(" ", resultLabel.getText());
        });
    }

    @Test
    void shouldHandleSuccessfulSearch() {
        SwingUtilities.invokeLater(() -> {
            // Setup
            searchField.setText("cat");
            languageCombo.setSelectedItem(Language.ENGLISH);
            
            // Simulate search
            DictionaryEntry entry = new DictionaryEntry("cat", "kucing");
            SearchResult result = new SearchResult(Optional.of(entry), List.of("Step 1", "Step 2"));
            DictionaryEvent event = new DictionaryEvent(
                DictionaryEventType.SEARCH_COMPLETED,
                "Search completed",
                result
            );
            
            // Trigger search
            searchButton.doClick();
            
            // Simulate event
            mainUI.onDictionaryEvent(event);
            
            // Verify
            assertTrue(resultLabel.getText().contains("Found: cat = kucing"));
            assertFalse(stepsArea.getText().isEmpty());
        });
    }

    @Test
    void shouldHandleWordNotFound() {
        SwingUtilities.invokeLater(() -> {
            // Setup
            searchField.setText("xyz");
            languageCombo.setSelectedItem(Language.ENGLISH);
            
            // Simulate not found result
            SearchResult result = new SearchResult(Optional.empty(), List.of("Step 1", "Not found"));
            DictionaryEvent event = new DictionaryEvent(
                DictionaryEventType.SEARCH_COMPLETED,
                "Search completed",
                result
            );
            
            // Trigger search
            searchButton.doClick();
            
            // Simulate event
            mainUI.onDictionaryEvent(event);
            
            // Verify
            assertEquals("Word not found", resultLabel.getText());
            assertTrue(stepsArea.getText().contains("Not found"));
        });
    }
}

class TestUtils {
    static Object getField(Object obj, String fieldName) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}