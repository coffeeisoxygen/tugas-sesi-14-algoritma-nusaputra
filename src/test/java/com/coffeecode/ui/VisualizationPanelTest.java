package com.coffeecode.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

class VisualizationPanelTest {
    private VisualizationPanel panel;

    @BeforeEach
    void setUp() {
        panel = new VisualizationPanel();
    }

    @Test
    void shouldStartWithEmptyState() {
        assertNull(panel.getCurrentSteps());
        assertEquals(-1, panel.getMidPointer());
        assertFalse(panel.isAnimating());
    }

    @Test
    void shouldUpdateVisualizationWithSteps() {
        List<String> steps = Arrays.asList(
            "Starting binary search for: cat",
            "Comparing with index 5: dog",
            "Moving to left half"
        );

        panel.updateSearchVisualization(steps);

        assertEquals(steps, panel.getCurrentSteps());
        assertEquals(0, panel.getCurrentStep());
        assertTrue(panel.isAnimating());
    }

    @Test
    void shouldClearVisualization() {
        panel.updateSearchVisualization(Arrays.asList("Step 1", "Step 2"));
        panel.clearVisualization();

        assertNull(panel.getCurrentSteps());
        assertEquals(-1, panel.getMidPointer());
        assertFalse(panel.isAnimating());
    }

    @Test
    void shouldHandleNullSteps() {
        panel.updateSearchVisualization(null);
        assertFalse(panel.isAnimating());
    }
}