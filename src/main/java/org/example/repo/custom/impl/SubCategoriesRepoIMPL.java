package org.example.repo.custom.impl;

import org.example.entity.custom.SubCategories;
import org.example.repo.custom.SubCategoriesRepo;
import org.example.util.CrudUtil;
import org.example.util.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean deleteAllWithBookId(int bookId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM sub_categories WHERE book_id = ?";
        return CrudUtil.execute(sql,bookId);
    }

    @Override
    public List<SubCategories> getAll(int bookId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM sub_categories WHERE book_id = ?";
        ResultSet execute = CrudUtil.execute(sql, bookId);
        List<SubCategories> subCategories = new ArrayList<>();
        while (execute.next()) {
            SubCategories subCategory = new SubCategories();
            subCategory.setBookId(execute.getInt(1));
            subCategory.setCategoryId(execute.getInt(2));
            subCategories.add(subCategory);
        }
        return subCategories;
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