package org.example.service.custom.impl;

import org.example.dto.custom.AuthorDTO;
import org.example.entity.custom.Author;
import org.example.repo.custom.AuthorRepo;
import org.example.service.custom.AuthorService;
import org.example.util.exceptions.custom.AuthorException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorServiceIMPL implements AuthorService {

    private final AuthorRepo repo;
    private final ModelMapper mapper;

    public AuthorServiceIMPL(ModelMapper mapper,AuthorRepo repo) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public boolean add(AuthorDTO authorDTO) throws AuthorException {
        Author author = convertToEntity(authorDTO);
        try {
            return repo.save(author);
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1062) {
                throw new AuthorException("ID Already Exists - Cannot Save.");
            } else if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new AuthorException("Data is To Large For "+s[1]);
            }
            throw new AuthorException("Error Occurred Please Contact Developer",e);
        }
    }

    @Override
    public boolean update(AuthorDTO authorDTO) throws AuthorException {
        Author author = convertToEntity(authorDTO);
        try {
            return repo.update(author);
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new AuthorException("Data is To Large For "+s[1]);
            }
            throw new AuthorException("Error Occurred Please Contact Developer",e);
        }

    }

    @Override
    public boolean delete(Integer integer) throws AuthorException {
        try {
            return repo.delete(integer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new AuthorException("Not Implemented Yet");
        }
    }

    @Override
    public Optional<AuthorDTO> search(Integer integer) throws AuthorException {
        try {
            Optional<Author> search = repo.search(integer);
            if (search.isPresent()) {
                return Optional.of(convertToDTO(search.get()));
            }
            return Optional.empty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new AuthorException("Please Contact Developer",e);
        }

    }

    @Override
    public List<AuthorDTO> getAll() throws AuthorException {
        try {
            List<Author> all = repo.getAll();
            //return all.stream().map(this::convertToDTO).toList();
            ArrayList<AuthorDTO> authorDTOS = new ArrayList<>();
            for (Author author : all) {
                AuthorDTO authorDTO = convertToDTO(author);
                authorDTOS.add(authorDTO);
            }
            return authorDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new AuthorException("Please Contact Developer",e);
        }
    }

    private AuthorDTO convertToDTO(Author author){
        return mapper.map(author,AuthorDTO.class);
    }

    private Author convertToEntity(AuthorDTO authorDTO){
        return mapper.map(authorDTO,Author.class);
    }
}