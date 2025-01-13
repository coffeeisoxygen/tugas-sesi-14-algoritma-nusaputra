package com.coffeecode.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SortVisualizerPanel extends JPanel {
    public SortVisualizerPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Sort Visualization"));
        
        // Placeholder
        JLabel placeholder = new JLabel("Sort Visualization", SwingConstants.CENTER);
        add(placeholder, BorderLayout.CENTER);
    }
}
