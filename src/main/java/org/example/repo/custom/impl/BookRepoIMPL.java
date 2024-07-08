package org.example.repo.custom.impl;

import org.example.entity.Book;
import org.example.repo.custom.BookRepo;
import org.example.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepoIMPL implements BookRepo {
    @Override
    public boolean save(Book book) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO book(id,name,isbn,price,publisher_id,main_category_id)" + " VALUES (?,?,?,?,?,?)";
        boolean execute = CrudUtil.execute(sql, book.getId(), book.getName(), book.getIsbn(), book.getPrice(), book.getPublisherId(), book.getMainCategoryId());
        return execute;
    }

    @Override
    public boolean update(Book book) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE book set name = ? , isbn = ? , price = ? , publisher_id = ? , " + "main_category_id = ? where id = ?";
        boolean execute = CrudUtil.execute(sql, book.getName(), book.getIsbn(), book.getPrice(), book.getPublisherId(), book.getMainCategoryId(), book.getId());
        return execute;
    }

    @Override
    public Optional<Book> search(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM book WHERE id = ?";
        ResultSet rs = CrudUtil.execute(sql, integer);
        if(rs.next()){
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setName(rs.getString(2));
            book.setIsbn(rs.getString(3));
            book.setPrice(rs.getDouble(4));
            book.setPublisherId(rs.getInt(5));
            book.setMainCategoryId(rs.getInt(6));
            return Optional.of(book);

        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM book WHERE id = ?";
        boolean execute = CrudUtil.execute(sql, integer);
        return execute;
    }

    @Override
    public List<Book> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM book";
        ResultSet rs = CrudUtil.execute(sql);
        List<Book> books = new ArrayList<>();
        while (rs.next()){
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setName(rs.getString(2));
            book.setIsbn(rs.getString(3));
            book.setPrice(rs.getDouble(4));
            book.setPublisherId(rs.getInt(5));
            book.setMainCategoryId(rs.getInt(6));
            books.add(book);
        }
        return books;
    }
}
