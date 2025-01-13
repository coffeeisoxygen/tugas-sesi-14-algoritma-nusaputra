package com.coffeecode.ui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> searchTypeCombo;

    public InputPanel() {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Search"));
        
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchTypeCombo = new JComboBox<>(new String[]{"English", "Indonesian"});

        add(searchField);
        add(searchTypeCombo);
        add(searchButton);
    }
}
