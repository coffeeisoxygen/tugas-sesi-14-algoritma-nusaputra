package com.coffeecode.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.Timer;

public class VisualizationPanel extends JPanel {
    private static final int BOX_WIDTH = 60;
    private static final int BOX_HEIGHT = 30;
    private static final int MARGIN = 50;
    private static final Color NORMAL_COLOR = Color.WHITE;
    private static final Color COMPARE_COLOR = Color.YELLOW;
    private static final Color FOUND_COLOR = Color.GREEN;
    private static final Color NOT_FOUND_COLOR = Color.RED;

    private List<String> currentSteps;
    private int currentStep;
    private Timer animationTimer;
    private int leftPointer = -1;
    private int rightPointer = -1;
    private int midPointer = -1;
    private Color[] boxColors;

    public VisualizationPanel() {
        setPreferredSize(new Dimension(800, 400));
        setupTimer();
    }

    private void setupTimer() {
        animationTimer = new Timer(1000, e -> {
            if (currentSteps != null && currentStep < currentSteps.size() - 1) {
                currentStep++;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
    }

    public void updateSearchVisualization(List<String> steps) {
        this.currentSteps = steps;
        this.currentStep = 0;
        this.boxColors = new Color[10]; // Assuming 10 elements for demo
        for (int i = 0; i < boxColors.length; i++) {
            boxColors[i] = NORMAL_COLOR;
        }
        animationTimer.restart();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (currentSteps == null || currentSteps.isEmpty())
            return;

        // Draw array boxes
        drawArrayBoxes(g2d);

        // Draw step information
        drawStepInfo(g2d);

        // Draw pointers
        drawPointers(g2d);
    }

    private void drawArrayBoxes(Graphics2D g2d) {
        int startX = MARGIN;
        int startY = getHeight() / 2;

        for (int i = 0; i < boxColors.length; i++) {
            int x = startX + (i * BOX_WIDTH);
            // Draw box
            g2d.setColor(boxColors[i]);
            g2d.fillRect(x, startY, BOX_WIDTH, BOX_HEIGHT);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, startY, BOX_WIDTH, BOX_HEIGHT);

            // Draw index
            g2d.drawString(String.valueOf(i), x + BOX_WIDTH / 3, startY + BOX_HEIGHT + 20);
        }
    }

    private void drawStepInfo(Graphics2D g2d) {
        if (currentStep >= 0 && currentStep < currentSteps.size()) {
            String step = currentSteps.get(currentStep);
            g2d.drawString(step, MARGIN, MARGIN);

            // Update visualization based on step
            updateVisualizationState(step);
        }
    }

    private void drawPointers(Graphics2D g2d) {
        int startY = getHeight() / 2 - 30;
        g2d.setColor(Color.BLUE);

        if (leftPointer >= 0) {
            int x = MARGIN + (leftPointer * BOX_WIDTH) + BOX_WIDTH / 2;
            g2d.drawString("L", x - 5, startY);
        }
        if (rightPointer >= 0) {
            int x = MARGIN + (rightPointer * BOX_WIDTH) + BOX_WIDTH / 2;
            g2d.drawString("R", x - 5, startY);
        }
        if (midPointer >= 0) {
            int x = MARGIN + (midPointer * BOX_WIDTH) + BOX_WIDTH / 2;
            g2d.drawString("M", x - 5, startY);
        }
    }

    private void updateVisualizationState(String step) {
        // Reset colors
        for (int i = 0; i < boxColors.length; i++) {
            boxColors[i] = NORMAL_COLOR;
        }

        // Parse step and update visualization
        if (step.contains("Comparing with index")) {
            Pattern pattern = Pattern.compile("index (\\d+)");
            Matcher matcher = pattern.matcher(step);
            if (matcher.find()) {
                midPointer = Integer.parseInt(matcher.group(1));
                boxColors[midPointer] = COMPARE_COLOR;
            }
        } else if (step.contains("Word found")) {
            if (midPointer >= 0)
                boxColors[midPointer] = FOUND_COLOR;
        } else if (step.contains("Word not found") && midPointer >= 0) {
            boxColors[midPointer] = NOT_FOUND_COLOR;
        }
    }
}
