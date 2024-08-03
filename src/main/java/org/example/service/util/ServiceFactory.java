package org.example.service.util;

import org.example.repo.util.RepoFactory;
import org.example.repo.util.RepoTypes;
import org.example.service.CrudService;
import org.example.service.SuperService;
import org.example.service.custom.*;
import org.example.service.custom.impl.*;

public class ServiceFactory {
    private static final RepoFactory repoFactory = RepoFactory.getInstance();
    private static final OtherDependancies otherDependancies = OtherDependancies.getInstance();
    private static final ServiceFactory getInstance = new ServiceFactory();

    private final BookService bookService;
    private final MemberService memberService;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    private ServiceFactory(){
        bookService = new BookServiceIMPL(otherDependancies.getMapper(),repoFactory.getRepo(RepoTypes.BOOK_REPO),
                repoFactory.getRepo(RepoTypes.BOOK_AUTHOR_REPO),repoFactory.getRepo(RepoTypes.SUB_CATEGORY_REPO));
        memberService = new MemberServiceIMPL(otherDependancies.getMapper(),repoFactory.getRepo(RepoTypes.MEMBER_REPO));
        publisherService = new PublisherServiceIMPL(otherDependancies.getMapper(),repoFactory.getRepo(RepoTypes.PUBLISHER_REPO));
        authorService = new AuthorServiceIMPL(otherDependancies.getMapper(),repoFactory.getRepo(RepoTypes.AUTHOR_REPO));
        categoryService = new CategoryServiceIMPL(otherDependancies.getMapper() , repoFactory.getRepo(RepoTypes.CATEGORY_REPO));
    }

    public static ServiceFactory getInstance(){
        return getInstance;
    }

    public SuperService getService(ServiceType type){
        return switch (type) {
            case BOOK_SERVICE -> bookService;
            case MEMBER_SERVICE -> memberService;
            case PUBLISHER_SERVICE -> publisherService;
            case AUTHOR_SERVICE -> authorService;
            case CATEGORY_SERVICE -> categoryService;
        };
    }
}