package com.coffeecode.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SearchVisualizerPanel extends JPanel {
    public SearchVisualizerPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Binary Search Visualization"));

        // Placeholder
        JLabel placeholder = new JLabel("Search Visualization", SwingConstants.CENTER);
        add(placeholder, BorderLayout.CENTER);
    }

}
