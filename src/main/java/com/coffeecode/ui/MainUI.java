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
import com.coffeecode.core.models.Language;
import com.coffeecode.core.service.BinarySearchDictionaryService;
import com.coffeecode.core.service.search.SearchResult;

public class MainUI extends JFrame implements DictionaryEventListener {
    private final transient BinarySearchDictionaryService service;
    private final JTextField searchField;
    private final JComboBox<Language> languageCombo;
    private final JTextArea stepsArea;
    private final JLabel resultLabel;
    private final VisualizationPanel visualizationPanel;
    private final JButton searchButton;

    public MainUI() {
        service = new BinarySearchDictionaryService();

        // Setup UI components
        setTitle("Dictionary Binary Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField(20);
        languageCombo = new JComboBox<>(Language.values());
        searchButton = new JButton("Search");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Search:"));
        inputPanel.add(searchField);
        inputPanel.add(languageCombo);
        inputPanel.add(searchButton);

        searchPanel.add(inputPanel, BorderLayout.NORTH);

        // Results Panel
        JPanel resultsPanel = new JPanel(new BorderLayout(5, 5));
        resultLabel = new JLabel(" ");
        stepsArea = new JTextArea(10, 40);
        stepsArea.setEditable(false);

        resultsPanel.add(resultLabel, BorderLayout.NORTH);
        resultsPanel.add(new JScrollPane(stepsArea), BorderLayout.CENTER);

        searchPanel.add(resultsPanel, BorderLayout.CENTER);

        // Visualization Panel
        visualizationPanel = new VisualizationPanel();

        // Add to frame
        add(searchPanel, BorderLayout.NORTH);
        add(visualizationPanel, BorderLayout.CENTER);

        // Add listeners
        searchButton.addActionListener(e -> performSearch());

        // Initialize
        service.initialize();

        // Display
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

        // Clear previous search
        visualizationPanel.clearVisualization();
        stepsArea.setText("");
        resultLabel.setText("Searching...");
        
        // Disable search until complete
        searchButton.setEnabled(false);
        
        // Subscribe to events
        DictionaryEventPublisher.subscribe(this);
        
        // Perform search in background
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
            switch (event.type()) {
                case SEARCH_COMPLETED -> {
                    SearchResult result = (SearchResult) event.data();
                    resultLabel.setText(result.getEntry()
                            .map(entry -> "Found: " + entry.getEnglish() + " = " + entry.getIndonesian())
                            .orElse("Word not found"));
                    stepsArea.setText(String.join("\n", result.getSteps()));
                    visualizationPanel.updateSearchVisualization(result.getSteps());
                }
                case DICTIONARY_LOADED -> {
                    int wordCount = (Integer) event.data();
                    setTitle("Dictionary Binary Search - " + wordCount + " words loaded");
                }
                case SEARCH_STARTED -> {
                    stepsArea.setText("Searching...");
                    resultLabel.setText("Searching for: " + event.message());
                }
                default -> throw new IllegalArgumentException("Unexpected value: " + event.type());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainUI().setVisible(true);
        });
    }
}
