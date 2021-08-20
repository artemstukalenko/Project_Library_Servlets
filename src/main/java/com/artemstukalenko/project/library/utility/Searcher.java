package com.artemstukalenko.project.library.utility;

import com.artemstukalenko.project.library.entity.Book;
import com.artemstukalenko.project.library.view.FirstView;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Searcher {
    String userInput;
    String searchCriteria;

    private FirstView textInfo = new FirstView();

    private Predicate<Book> searchByTitle = book -> {return book.getBookTitle().toLowerCase()
            .contains(userInput.toLowerCase());};
    private Predicate <Book> searchByAuthor = book -> {return book.getBookAuthor().toLowerCase()
            .contains(userInput.toLowerCase());};
    private Predicate <Book> searchByYear = book -> {return book.getBookYearOfPublishing().toLowerCase()
            .contains(userInput.toLowerCase());};

    public Searcher() {}

    public List<Book> getResultOfTheBookSearch(List<Book> allBooksList) {
        switch (searchCriteria) {
            case "byTitle":
                return allBooksList.stream().filter(searchByTitle)
                        .collect(Collectors.toList());
            case "byAuthor":
                return allBooksList.stream().filter(searchByAuthor)
                        .collect(Collectors.toList());
            case "byYear":
                return allBooksList.stream().filter(searchByYear)
                        .collect(Collectors.toList());
            default:
                return allBooksList.stream().filter(searchByTitle)
                        .collect(Collectors.toList());
        }

    }

    private void commandTranslator() {
        if (this.searchCriteria.equals(textInfo.getFilterByTitle())) {
            searchCriteria = "By title";
        } else if (this.searchCriteria.equals(textInfo.getFilterByAuthor())) {
            searchCriteria = "By author";
        } else if (this.searchCriteria.equals(textInfo.getFilterByYear())) {
            searchCriteria = "By year";
        }
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
