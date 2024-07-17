package org.example.service.custom.impl;

import org.example.dto.custom.AuthorDTO;
import org.example.entity.custom.Author;
import org.example.repo.custom.AuthorRepo;
import org.example.repo.custom.impl.AuthorRepoIMPL;
import org.example.service.custom.AuthorService;
import org.example.util.exceptions.ServiceException;
import org.example.util.exceptions.custom.AuthorExceptions;
import org.example.util.exceptions.custom.BookException;
import org.example.util.exceptions.custom.MemberException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class AuthorServiceIMPL implements AuthorService {
    //property injection - not a recommended
    final AuthorRepo authorRepo;
    final ModelMapper mapper;

    //constructor through injection - recommended
    public AuthorServiceIMPL(ModelMapper mapper,AuthorRepo authorRepo){
        this.authorRepo = authorRepo;
        this.mapper = mapper;
    }
    //setter method injection - normally recommended
    /*public void setAuthorRepo(AuthorRepo authorRepo){
        this.authorRepo = authorRepo;
    }*/

    @Override
    public boolean add(AuthorDTO authorDTO) throws AuthorExceptions {
        Author author = convertToEntity(authorDTO);
        try {
            return authorRepo.save(author);
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1062) {
                throw new AuthorExceptions("ID Already Exists - Cannot Save.");
            } else if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new AuthorExceptions("Data is To Large For "+s[1]);
            }
            throw new AuthorExceptions("Error Occurred Please Contact Developer",e);
        }
    }

    @Override
    public boolean update(AuthorDTO authorDTO) throws AuthorExceptions {
        Author author = convertToEntity(authorDTO);
        try {
            return authorRepo.update(author);
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new AuthorExceptions("Data is To Large For "+s[1]);
            }
            throw new AuthorExceptions("Error Occurred Please Contact Developer",e);
        }
    }

    @Override
    public boolean delete(Integer integer) throws AuthorExceptions {
        try {
            return authorRepo.delete(integer);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new AuthorExceptions("Error Occurred Please Contact Developer", e);
        }

    }

    @Override
    public Optional<AuthorDTO> search(Integer integer) throws AuthorExceptions {
        try {
            Optional<Author> search = authorRepo.search(integer);
            if (search.isPresent()){
                Author author = search.get();
                AuthorDTO authorDTO = convertToDto(author);
                return Optional.of(authorDTO);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new AuthorExceptions("Please Contact Developer",e);
        }
        return Optional.empty();
    }

    @Override
    public List<AuthorDTO> getAll() throws AuthorExceptions {
        try {
            List<Author> all = authorRepo.getAll();
            return all.stream().map(this::convertToDto).toList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new AuthorExceptions("Please Contact Developer",e);
        }
    }

    public Author convertToEntity(AuthorDTO authorDTO) {
        return this.mapper.map(authorDTO, Author.class);
    }

    private AuthorDTO  convertToDto(Author author){
        return this.mapper.map(author,AuthorDTO.class);
    }

}