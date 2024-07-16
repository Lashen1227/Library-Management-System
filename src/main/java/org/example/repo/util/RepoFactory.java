package org.example.repo.util;

import org.example.repo.CrudRepository;
import org.example.repo.custom.*;
import org.example.repo.custom.impl.*;

//Factory Design Pattern
//Singleton
public class RepoFactory {

    private static final RepoFactory instance = new RepoFactory();

    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final MemberRepo memberRepo;
    private final PublisherRepo publisherRepo;


    //constructor private (call once in whole project) - 1
    private RepoFactory(){
        authorRepo = new AuthorRepoIMPL(); // - 1
        bookRepo = new BookRepoIMPL();// - 1
        categoryRepo = new CategoryRepoIMPL();// -1
        memberRepo = new MemberRepoIMPL();// -1
        publisherRepo = new PublisherRepoIMPL();// -1
    }

    public <T extends CrudRepository>T getRepo(RepoTypes type){
        switch (type){
            case BOOK_REPO : return (T) this.bookRepo;
            case AUTHOR_REPO : return (T) this.authorRepo;
            case CATEGORY_REPO : return (T) this.categoryRepo;
            case MEMBER_REPO : return (T) this.memberRepo;
            case PUBLISHER_REPO : return (T) this.publisherRepo;
            default : return null;
        }
    }

    public static RepoFactory getInstance(){
        return instance;
    }

}
