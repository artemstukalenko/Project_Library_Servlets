package com.artemstukalenko.project.library.utility;

import com.artemstukalenko.project.library.entity.Book;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorter {

    private String sortMethod;

    private Comparator<Book> byTitle = (b1, b2) -> {return b1.getBookTitle().compareTo(b2.getBookTitle());};
    private Comparator<Book> byAuthor = (b1, b2) -> {return b1.getBookAuthor().compareTo(b2.getBookAuthor());};
    private Comparator<Book> byYear = (b1, b2) -> {return b1.getBookYearOfPublishing().compareTo(b2.getBookYearOfPublishing());};

    public Sorter() {}

    public List<Book> sortList(List<Book> listToSort) {
        switch (sortMethod) {
            case "By title":
                return listToSort.stream().sorted(byTitle)
                        .collect(Collectors.toList());
            case "By author":
                return listToSort.stream().sorted(byAuthor)
                        .collect(Collectors.toList());
            case "By year":
                return listToSort.stream().sorted(byYear)
                        .collect(Collectors.toList());
            default:
                return listToSort.stream().sorted(byTitle)
                        .collect(Collectors.toList());
        }
    }

    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }
}