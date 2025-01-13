package com.coffeecode.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainUI extends JFrame {
    private WordsPanel wordsPanel;
    private SortVisualizerPanel sortPanel;
    private SearchVisualizerPanel searchPanel;
    private InputPanel inputPanel;

    public MainUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Dictionary Binary Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize panels
        wordsPanel = new WordsPanel();
        sortPanel = new SortVisualizerPanel();
        searchPanel = new SearchVisualizerPanel();
        inputPanel = new InputPanel();

        // Layout setup
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(wordsPanel, BorderLayout.CENTER);
        leftPanel.add(inputPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.add(sortPanel);
        rightPanel.add(searchPanel);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainUI().setVisible(true);
        });
    }
}
