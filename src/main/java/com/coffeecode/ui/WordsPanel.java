package com.coffeecode.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WordsPanel extends JPanel {
    private JTable wordsTable;
    private DefaultTableModel tableModel;

    public WordsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Dictionary"));
        initializeTable();
    }

    private void initializeTable() {
        String[] columnNames = {"English", "Indonesian"};
        tableModel = new DefaultTableModel(columnNames, 0);
        wordsTable = new JTable(tableModel);
        
        // Add sample data
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(wordsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"apple", "apel"});
        tableModel.addRow(new Object[]{"banana", "pisang"});
        tableModel.addRow(new Object[]{"orange", "jeruk"});
    }
}
