package org.example.repo.custom;

import org.example.entity.custom.SubCategories;
import org.example.repo.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface SubCategoriesRepo extends CrudRepository<SubCategories,Integer> {
    public boolean saveList(List<SubCategories> list) throws SQLException, ClassNotFoundException;

    public boolean deleteAllWithBookId(int bookId) throws SQLException, ClassNotFoundException;

    public List<SubCategories> getAll(int bookId) throws SQLException, ClassNotFoundException;
}