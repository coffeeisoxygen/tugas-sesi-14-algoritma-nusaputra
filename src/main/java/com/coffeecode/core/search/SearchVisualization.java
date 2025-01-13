package com.coffeecode.core.search;

import java.util.ArrayList;
import java.util.List;

public class SearchVisualization {
    private List<String> steps = new ArrayList<>();
    
    public void addStep(String step) {
        steps.add(step);
    }
    
    public void addComparisonStep(int index, String word, String compareWith) {
        steps.add(String.format("Compare at index %d: '%s' with '%s'", index, word, compareWith));
    }
    
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }
}