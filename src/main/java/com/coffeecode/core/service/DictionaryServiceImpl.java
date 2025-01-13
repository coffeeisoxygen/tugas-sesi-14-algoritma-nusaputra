// package com.coffeecode.core.service;

// import com.coffeecode.core.models.*;
// import com.coffeecode.core.repository.DictionaryRepository;

// public class DictionaryServiceImpl implements DictionaryService {
// private final DictionaryRepository repository;
// private final BinarySearch binarySearch;
// private List<DictionaryEntri> sortedEntries;

// public DictionaryServiceImpl(DictionaryRepository repository, Language
// sortLanguage) {
// this.repository = repository;
// this.sortedEntries = new
// MergeSort(sortLanguage).sort(repository.loadEntries());
// this.binarySearch = new BinarySearch(sortedEntries);
// }

// @Override
// public String translate(String word, Language from, Language to) {
// SearchResult result = binarySearch.find(word, from);
// return result.getTranslation()
// .orElseThrow(() -> new CustomException("Word not found: " + word));
// }

// @Override
// public List<String> getSearchSteps(String word, Language from) {
// SearchResult result = binarySearch.find(word, from);
// return result.getSearchSteps();
// }

// @Override
// public List<String> getSortSteps() {
// return ((MergeSort)sorter).getSortSteps();
// }
// }