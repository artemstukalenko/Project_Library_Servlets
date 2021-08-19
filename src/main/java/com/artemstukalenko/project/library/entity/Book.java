package com.artemstukalenko.project.library.entity;

import java.util.Objects;

public class Book {

    private int bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookYearOfPublishing;
    private boolean taken;

    public Book() {this.taken = false;}

    public Book(String bookTitle, String bookAuthor, String bookYearOfPublishing) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookYearOfPublishing = bookYearOfPublishing;
        this.taken = false;
    }

    public Book(int bookId, String bookTitle, String bookAuthor, String bookYearOfPublishing, boolean taken) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookYearOfPublishing = bookYearOfPublishing;
        this.taken = taken;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookYearOfPublishing() {
        return bookYearOfPublishing;
    }

    public void setBookYearOfPublishing(String bookYearOfPublishing) {
        this.bookYearOfPublishing = bookYearOfPublishing;
    }

    public boolean getTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookYearOfPublishing='" + bookYearOfPublishing + '\'' +
                ", taken=" + taken +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && taken == book.taken && bookTitle.equals(book.bookTitle)
                && bookAuthor.equals(book.bookAuthor) && bookYearOfPublishing.equals(book.bookYearOfPublishing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookTitle, bookAuthor, bookYearOfPublishing, taken);
    }
}
