package org.example.repo.custom.impl;

import org.example.entity.custom.SubCategories;
import org.example.repo.custom.SubCategoriesRepo;
import org.example.util.CrudUtil;
import org.example.util.DBConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SubCategoriesRepoIMPL implements SubCategoriesRepo {
    boolean flag = true;
    @Override
    public boolean saveList(List<SubCategories> list) throws SQLException, ClassNotFoundException {
        flag = true;
        for (SubCategories subCategory : list) {
            if (flag) {
                save(subCategory);
            }else{
                return false;
            }
        }
        return true;
    }

    @Override
    public SubCategories save(SubCategories subCategories) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO sub_categories(book_id,category_id) VALUES (?,?)";
        flag =CrudUtil.execute(sql,subCategories.getBookId(),subCategories.getCategoryId());
        return subCategories;
    }

    @Override
    public boolean update(SubCategories subCategories) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Optional<SubCategories> search(Integer integer) throws SQLException, ClassNotFoundException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<SubCategories> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }
}
