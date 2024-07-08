package org.example.repo.custom;

import org.example.entity.Author;
import org.example.repo.CrudRepository;

public interface AuthorRepo extends CrudRepository<Author,Integer> {
}
