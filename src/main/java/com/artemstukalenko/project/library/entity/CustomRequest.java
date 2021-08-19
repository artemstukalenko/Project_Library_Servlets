package com.artemstukalenko.project.library.entity;

import java.time.LocalDate;
import java.util.Objects;

public class CustomRequest {

    private int customRequestId;
    private String username;
    private int bookId;
    private String title;
    private String author;
    private LocalDate startOfThePeriod;
    private LocalDate endOfThePeriod;

    public CustomRequest() {
    }

    public CustomRequest(String username, int bookId, String title, String author, LocalDate startOfThePeriod,
                         LocalDate endOfThePeriod) {
        this.username = username;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.startOfThePeriod = startOfThePeriod;
        this.endOfThePeriod = endOfThePeriod;
    }

    public CustomRequest(int customRequestId, String username, int bookId, String title, String author,
                         LocalDate startOfThePeriod, LocalDate endOfThePeriod) {
        this.customRequestId = customRequestId;
        this.username = username;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.startOfThePeriod = startOfThePeriod;
        this.endOfThePeriod = endOfThePeriod;
    }

    public int getCustomRequestId() {
        return customRequestId;
    }

    public void setCustomRequestId(int customRequestId) {
        this.customRequestId = customRequestId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getStartOfThePeriod() {
        return startOfThePeriod;
    }

    public void setStartOfThePeriod(LocalDate startOfThePeriod) {
        this.startOfThePeriod = startOfThePeriod;
    }

    public LocalDate getEndOfThePeriod() {
        return endOfThePeriod;
    }

    public void setEndOfThePeriod(LocalDate endOfThePeriod) {
        this.endOfThePeriod = endOfThePeriod;
    }

    @Override
    public String toString() {
        return "CustomSubscriptionRequestService{" +
                "customSubscriptionId=" + customRequestId +
                ", username='" + username + '\'' +
                ", bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", startOfThePeriod=" + startOfThePeriod +
                ", endOfThePeriod=" + endOfThePeriod +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomRequest that = (CustomRequest) o;
        return customRequestId == that.customRequestId && bookId == that.bookId && username.equals(that.username) && title.equals(that.title) && author.equals(that.author) && startOfThePeriod.equals(that.startOfThePeriod) && endOfThePeriod.equals(that.endOfThePeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customRequestId, username, bookId, title, author, startOfThePeriod, endOfThePeriod);
    }
}