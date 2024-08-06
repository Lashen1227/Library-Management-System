package org.example.service.custom.impl;

import org.example.dto.custom.BookDTO;
import org.example.entity.custom.Book;
import org.example.entity.custom.BookAuthor;
import org.example.entity.custom.SubCategories;
import org.example.repo.custom.BookAuthorRepo;
import org.example.repo.custom.BookRepo;
import org.example.repo.custom.SubCategoriesRepo;
import org.example.repo.custom.impl.BookRepoIMPL;
import org.example.service.custom.BookService;
import org.example.util.DBConnection;
import org.example.util.exceptions.custom.BookException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class BookServiceIMPL implements BookService {
    private final ModelMapper mapper;
    private final BookRepo repo;
    private final BookAuthorRepo bookAuthorRepo;
    private final SubCategoriesRepo subCategoriesRepo;

    public BookServiceIMPL(ModelMapper mapper, BookRepo repo, BookAuthorRepo bookAuthorRepo, SubCategoriesRepo subCategoriesRepo) {
        this.mapper = mapper;
        this.repo = repo;
        this.bookAuthorRepo = bookAuthorRepo;
        this.subCategoriesRepo = subCategoriesRepo;
    }

    @Override
    public boolean add(BookDTO bookDTO) throws BookException {
        Book book = this.convertDtoToEntity(bookDTO);
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            Book save = repo.save(book);
            List<BookAuthor> authorList = bookDTO.getAuthors().stream().map(e -> new BookAuthor(save.getId(), e)).toList();
            List<SubCategories> subCategoryList = bookDTO.getSubCategoryIds().stream().map(e -> new SubCategories(save.getId(), e)).toList();

            boolean flag = bookAuthorRepo.saveList(authorList);
            if (flag){
                flag = subCategoriesRepo.saveList(subCategoryList);
                if (flag){
                    DBConnection.getInstance().getConnection().commit();
                    return true;
                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;

        } catch (SQLException | ClassNotFoundException e) {
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException | ClassNotFoundException ex) {

            }
            if (((SQLException) e).getErrorCode() == 1062) {
                throw new BookException("ID Already Exists - Cannot Save.");
            } else if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new BookException("Data is To Large For "+s[1]);
            }
            throw new BookException("Error Occured Please Contact Developer",e);
        }finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException | ClassNotFoundException e) {

            }
        }
    }

    @Override
    public boolean update(BookDTO bookDTO) throws BookException {
        Book book = convertDtoToEntity(bookDTO);
        List<BookAuthor> authorList = bookDTO.getAuthors().stream().map(e -> new BookAuthor(bookDTO.getId(), e)).toList();
        List<SubCategories> subCategoryList = bookDTO.getSubCategoryIds().stream().map(e -> new SubCategories(bookDTO.getId(), e)).toList();
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            //book table update
            boolean update = repo.update(book);
            if (update){
                //bookAuthor table records clear
                boolean isDeletedBookAuthor = bookAuthorRepo.deleteALlWithBookId(bookDTO.getId());
                if (isDeletedBookAuthor){
                    //subcategories table records clear
                    boolean isAllSUbCategoriesDeleted = subCategoriesRepo.deleteAllWithBookId(bookDTO.getId());
                    if (isAllSUbCategoriesDeleted){
                        //bookAuthor table records add
                        boolean isALlBookAuthorRecordsAdded = bookAuthorRepo.saveList(authorList);
                        if (isALlBookAuthorRecordsAdded){
                            //subcategories table records add
                            boolean isAllSubCategoryRecordsAdded = subCategoriesRepo.saveList(subCategoryList);
                            if (isAllSubCategoryRecordsAdded){
                                DBConnection.getInstance().getConnection().commit();
                                return true;
                            }
                        }
                    }
                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException | ClassNotFoundException ex) {}
            if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new BookException("Data is To Large For "+s[1]);
            }
            throw new BookException("Error Occured Please Contact Developer",e);
        }finally {
            try {DBConnection.getInstance().getConnection().setAutoCommit(true);} catch (SQLException | ClassNotFoundException e) {}
        }

    }

    @Override
    public boolean delete(Integer integer) throws BookException {
        try {
            return repo.delete(integer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("Not Implemented Yet Fully",e);
        }
    }

    @Override
    public Optional<BookDTO> search(Integer integer) throws BookException {
        try {
            Optional<Book> search = repo.search(integer);
            if (search.isPresent()){
                Book book = search.get();
                List<Integer> authors = bookAuthorRepo.getAll(integer).stream().map(e -> e.getAuthorId()).toList();
                List<Integer> subCategoryIds = subCategoriesRepo.getAll(integer).stream().map(e -> e.getCategoryId()).toList();
                BookDTO bookDTO = convertEntityToDto(book);
                bookDTO.setSubCategoryIds(subCategoryIds);
                bookDTO.setAuthors(authors);
                return Optional.of(bookDTO);
            }
            return Optional.empty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("Please Contact Developper",e);
        }

    }

    @Override
    public List<BookDTO> getAll() throws BookException {
        try {
            List<Book> all = repo.getAll();
            List<BookDTO> newList = new ArrayList<>();
            for (Book book : all) {
                BookDTO bookDTO = convertEntityToDto(book);
                newList.add(bookDTO);
            }
            return newList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("Please Contact Developper",e);
        }
    }

    private Book convertDtoToEntity(BookDTO dto){
        return mapper.map(dto,Book.class);
    }

    public BookDTO convertEntityToDto(Book book){
        return mapper.map(book,BookDTO.class);
    }
}