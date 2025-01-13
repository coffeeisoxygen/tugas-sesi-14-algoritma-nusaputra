package com.coffeecode.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.coffeecode.core.events.DictionaryEvent;
import com.coffeecode.core.events.DictionaryEventListener;
import com.coffeecode.core.events.DictionaryEventPublisher;
import com.coffeecode.core.events.DictionaryEventType;
import com.coffeecode.core.models.Language;
import com.coffeecode.core.service.BinarySearchDictionaryService;
import com.coffeecode.core.service.search.SearchResult;

public class MainUI extends JFrame implements DictionaryEventListener {
    private final BinarySearchDictionaryService service;
    private final JTextField searchField;
    private final JComboBox<Language> languageCombo;
    private final JTextArea stepsArea;
    private final JLabel resultLabel;
    private final VisualizationPanel visualizationPanel;
    private final JButton searchButton;

    public MainUI() {
        service = new BinarySearchDictionaryService();
        setTitle("Dictionary Binary Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        languageCombo = new JComboBox<>(Language.values());
        searchButton = new JButton("Search");
        
        inputPanel.add(new JLabel("Search:"));
        inputPanel.add(searchField);
        inputPanel.add(languageCombo);
        inputPanel.add(searchButton);

        // Results Panel
        JPanel resultsPanel = new JPanel(new BorderLayout(5, 5));
        resultLabel = new JLabel(" ");
        stepsArea = new JTextArea(10, 40);
        stepsArea.setEditable(false);
        
        resultsPanel.add(resultLabel, BorderLayout.NORTH);
        resultsPanel.add(new JScrollPane(stepsArea), BorderLayout.CENTER);

        // Visualization Panel
        visualizationPanel = new VisualizationPanel();

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(resultsPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(visualizationPanel, BorderLayout.CENTER);

        // Event Handlers
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());
        
        // Initialize
        DictionaryEventPublisher.subscribe(this);
        service.initialize();
        
        pack();
        setLocationRelativeTo(null);
    }

    private void performSearch() {
        String word = searchField.getText().trim();
        Language language = (Language) languageCombo.getSelectedItem();

        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a word to search");
            return;
        }

        visualizationPanel.clearVisualization();
        stepsArea.setText("");
        resultLabel.setText("Searching...");
        searchButton.setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                service.searchWord(word, language);
                return null;
            }

            @Override
            protected void done() {
                searchButton.setEnabled(true);
            }
        };
        worker.execute();
    }

    @Override
    public void onDictionaryEvent(DictionaryEvent event) {
        SwingUtilities.invokeLater(() -> {
            if (event.type() == DictionaryEventType.SEARCH_COMPLETED) {
                SearchResult result = (SearchResult) event.data();
                resultLabel.setText(result.getEntry()
                    .map(entry -> "Found: " + entry.getEnglish() + " = " + entry.getIndonesian())
                    .orElse("Word not found"));
                stepsArea.setText(String.join("\n", result.getSteps()));
                visualizationPanel.updateSearchVisualization(result.getSteps());
            }
            else if (event.type() == DictionaryEventType.DICTIONARY_LOADED) {
                setTitle("Dictionary Binary Search - " + event.data() + " words loaded");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }
}
