package com.artemstukalenko.project.library.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Subscription {

    private int subscriptionId;
    private String username;
    private int bookId;
    private String title;
    private String author;
    private final LocalDate startOfThePeriod;
    private final LocalDate endOfThePeriod;
    private boolean expired;
    private boolean fined;

    public Subscription() {
        this.startOfThePeriod = LocalDate.now();
        this.endOfThePeriod = LocalDate.now().plusMonths(1);
        this.fined = false;
    }

    public Subscription(String username, int bookId, String title, String author) {
        this.username = username;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.startOfThePeriod = LocalDate.now();
        this.endOfThePeriod = LocalDate.now().plusMonths(1);
        this.fined = false;
    }

    public Subscription(String username, int bookId, String title, String author,
                        LocalDate startOfThePeriod, LocalDate endOfThePeriod) {
        this.username = username;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.startOfThePeriod = startOfThePeriod;
        this.endOfThePeriod = endOfThePeriod;
        this.fined = false;
    }

//    public Subscription(CustomSubscriptionRequest request) {
//        this.username = request.getUsername();
//        this.bookId = request.getBookId();
//        this.title = request.getTitle();
//        this.author = request.getAuthor();
//        this.startOfThePeriod = request.getStartOfThePeriod();
//        this.endOfThePeriod = request.getEndOfThePeriod();
//        this.fined = false;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBook(Book book) {
        this.bookId = book.getBookId();
    }

    public LocalDate getStartOfThePeriod() {
        return startOfThePeriod;
    }

    public LocalDate getEndOfThePeriod() {
        return endOfThePeriod;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public boolean getExpired() {
        return LocalDate.now().isAfter(endOfThePeriod);
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

    public boolean getFined() {
        return fined;
    }

    public void setFined(boolean fined) {
        this.fined = fined;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "username='" + username + '\'' +
                ", bookId=" + bookId +
                ", startOfThePeriod=" + startOfThePeriod +
                ", endOfThePeriod=" + endOfThePeriod +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return subscriptionId == that.subscriptionId && bookId == that.bookId && expired == that.expired && fined == that.fined && username.equals(that.username) && title.equals(that.title) && author.equals(that.author) && startOfThePeriod.equals(that.startOfThePeriod) && endOfThePeriod.equals(that.endOfThePeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionId, username, bookId, title, author, startOfThePeriod, endOfThePeriod, expired, fined);
    }
}
