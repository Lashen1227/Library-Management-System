package org.example.repo.custom;

import org.example.entity.custom.Book;
import org.example.repo.CrudRepository;

public interface BookRepo extends CrudRepository<Book,Integer> {
}
