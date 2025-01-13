// package com.coffeecode.core.service;

// public class BinarySearch implements VocabularySearchable {
// private final List<DictionaryEntri> sortedEntries;
// private final SearchVisualization visualization;

// public BinarySearch(List<DictionaryEntri> sortedEntries) {
// this.sortedEntries = sortedEntries;
// this.visualization = new SearchVisualization();
// }

// @Override
// public SearchResult find(String word, Language language) {
// visualization.addStep("Starting search for: " + word);
// Optional<DictionaryEntri> result = search(word, language);
// return new SearchResult(
// result.map(entry -> entry.getTranslation(language == Language.INDONESIAN ?
// Language.ENGLISH : Language.INDONESIAN)),
// visualization.getSteps()
// );
// }

// private Optional<DictionaryEntri> search(String word, Language language) {
// int left = 0;
// int right = sortedEntries.size() - 1;

// while (left <= right) {
// int mid = left + (right - left) / 2;
// String midWord = sortedEntries.get(mid).getTranslation(language);

// visualization.addComparisonStep(mid, word, midWord);

// int comparison = word.compareToIgnoreCase(midWord);
// if (comparison == 0) {
// visualization.addStep("Word found at index: " + mid);
// return Optional.of(sortedEntries.get(mid));
// }
// if (comparison > 0) {
// visualization.addStep("Moving to right half");
// left = mid + 1;
// } else {
// visualization.addStep("Moving to left half");
// right = mid - 1;
// }
// }

// visualization.addStep("Word not found");
// return Optional.empty();
// }
// }