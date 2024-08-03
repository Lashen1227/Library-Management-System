package org.example.repo.custom;

import org.example.entity.custom.SubCategories;
import org.example.repo.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface SubCategoriesRepo extends CrudRepository<SubCategories,Integer> {
    public boolean saveList(List<SubCategories> list) throws SQLException, ClassNotFoundException;
}