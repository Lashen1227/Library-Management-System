package org.example.repo.custom.impl;

import org.example.entity.custom.BookAuthor;
import org.example.repo.custom.BookAuthorRepo;
import org.example.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookAuthorRepoImpl implements BookAuthorRepo {
    boolean flag = true;
    @Override
    public boolean saveList(List<BookAuthor> list) throws SQLException, ClassNotFoundException {
        flag = true;
        for(BookAuthor bookAuthor:list){
            if (flag) {
                save(bookAuthor);
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public BookAuthor save(BookAuthor bookAuthor) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO book_author(book_id,author_id) VALUES (?,?)";
        flag = CrudUtil.execute(sql, bookAuthor.getBookId(), bookAuthor.getAuthorId());
        return bookAuthor;
    }

    @Override
    public boolean update(BookAuthor bookAuthor) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Optional<BookAuthor> search(Integer integer) throws SQLException, ClassNotFoundException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<BookAuthor> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }
}