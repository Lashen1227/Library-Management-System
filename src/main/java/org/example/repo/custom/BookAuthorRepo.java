package org.example.repo.custom;

import org.example.entity.custom.BookAuthor;
import org.example.repo.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface BookAuthorRepo extends CrudRepository<BookAuthor,Integer> {
    public boolean saveList(List<BookAuthor> list) throws SQLException, ClassNotFoundException;
}