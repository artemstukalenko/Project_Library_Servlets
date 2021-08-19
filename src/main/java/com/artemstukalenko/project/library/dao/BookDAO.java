package com.artemstukalenko.project.library.dao;

import com.artemstukalenko.project.library.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {

    public List<Book> getAllBooks() throws SQLException;

    public boolean deleteBook(int bookId) throws SQLException;

    public boolean addNewBook(Book bookToAdd) throws SQLException;

    public Book findBookById(int bookId) throws SQLException;

    public boolean setTaken(int id, boolean taken) throws SQLException;

}
