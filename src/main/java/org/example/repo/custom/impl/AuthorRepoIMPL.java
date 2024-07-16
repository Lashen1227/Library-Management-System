package org.example.repo.custom.impl;

import org.example.entity.custom.Author;
import org.example.repo.custom.AuthorRepo;
import org.example.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepoIMPL implements AuthorRepo {
    @Override
    public boolean save(Author author) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Author(name,contact) VALUES (?,?)";
        return CrudUtil.execute(sql,author.getName(),author.getContact());
    }

    @Override
    public boolean update(Author author) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Author SET name=?,contact=? WHERE id=?";
        return CrudUtil.execute(sql,author.getName(),author.getContact(),author.getId());
    }

    @Override
    public Optional<Author> search(Integer s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Author WHERE id= ?" ;
        ResultSet execute = CrudUtil.execute(sql,s);
        if (execute.next()) {
            Author author = new Author();
            author.setId(execute.getInt(1));
            author.setName(execute.getString(2));
            author.setContact(execute.getString(3));
            return Optional.of(author);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer i) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Author WHERE id=?";
        return CrudUtil.execute(sql,i);
    }

    @Override
    public List<Author> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Author";
        ResultSet execute = CrudUtil.execute(sql);
        List<Author> authors = new ArrayList<>();
        while (execute.next()){
            Author author = new Author();
            author.setId(execute.getInt(1));
            author.setName(execute.getString(2));
            author.setContact(execute.getString(3));
            authors.add(author);
        }
        return authors;
    }
}