package org.example.service;

import org.example.util.exceptions.MemberException;

import java.util.List;
import java.util.Optional;

public interface CrudService <T,ID> {
    boolean add(T t) throws MemberException;
    boolean update(T t) throws MemberException;
    boolean delete(ID id) throws MemberException;
    Optional<T> search(ID id) ;
    List<T> getAll() throws MemberException;
}
