package org.example.service;

import org.example.dto.SuperDTO;
import org.example.util.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CrudService <T extends SuperDTO,ID> {
    boolean add(T t) throws ServiceException;
    boolean update(T t) throws ServiceException;
    boolean delete(ID id) throws ServiceException;
    Optional<T> search(ID id) throws ServiceException;
    List<T> getAll() throws ServiceException;
}
