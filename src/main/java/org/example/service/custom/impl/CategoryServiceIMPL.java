package org.example.service.custom.impl;

import org.example.dto.custom.CategoryDTO;
import org.example.entity.custom.Category;
import org.example.repo.custom.CategoryRepo;
import org.example.service.custom.CategoryService;
import org.example.util.exceptions.ServiceException;
import org.example.util.exceptions.custom.CategoryException;
import org.example.util.exceptions.custom.MemberException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoryServiceIMPL implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ModelMapper mapper;

    public CategoryServiceIMPL(ModelMapper mapper,CategoryRepo repo){
        this.categoryRepo = repo;
        this.mapper = mapper;
    }

    @Override
    public boolean add(CategoryDTO categoryDTO) throws CategoryException {
        Category category = convertToEntity(categoryDTO);
        try {
            return categoryRepo.save(category);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException) {
                System.out.println("Error Code : "+((SQLException) e).getErrorCode());
                if (((SQLException) e).getErrorCode() == 1062) {
                    throw new CategoryException("ID Already Exists - Cannot Save.");
                } else if (((SQLException) e).getErrorCode() == 1406) {
                    String message = ((SQLException) e).getMessage();
                    String[] s = message.split("'");
                    throw new CategoryException("Data is To Large For "+s[1]);
                }
            }
            e.printStackTrace();
            throw new CategoryException("Error Occurred Please Contact Developer",e);
        }
    }

    @Override
    public boolean update(CategoryDTO categoryDTO) throws CategoryException {
        Category category = convertToEntity(categoryDTO);
        try {
            return categoryRepo.update(category);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException){
                if (((SQLException) e).getErrorCode() == 1406) {
                    String message = ((SQLException) e).getMessage();
                    String[] s = message.split("'");
                    throw new CategoryException("Data is To Large For "+s[1]);
                }
            }
            throw new CategoryException("Error Occurred Please Contact Developer",e);
        }
    }

    @Override
    public boolean delete(Integer id) throws CategoryException {
        try {
            return categoryRepo.delete(id);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new CategoryException("Error Occurred Please Contact Developer", e);
        }
    }

    @Override
    public Optional<CategoryDTO> search(Integer id) throws CategoryException {
        try {
            Optional<Category> search = categoryRepo.search(id);
            if (search.isPresent()) {
                return Optional.of(convertToDTO(search.get()));
            }
            return Optional.empty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new CategoryException("Please Contact Developer",e);
        }
    }

    @Override
    public List<CategoryDTO> getAll() throws CategoryException {
        try {
            return categoryRepo.getAll().stream()
                    .map(this::convertToDTO).toList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new CategoryException("Please Contact Developer",e);
        }
    }

    private Category convertToEntity(CategoryDTO categoryDTO){
        return mapper.map(categoryDTO, Category.class);
    }

    private CategoryDTO convertToDTO(Category category){
        return mapper.map(category, CategoryDTO.class);
    }

}
