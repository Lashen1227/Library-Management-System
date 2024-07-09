package org.example.entity.custom;

import org.example.entity.SuperEntity;

public class Book implements SuperEntity {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private String author;
    private int publisherId;
    private int mainCategoryId;

    public Book() {
    }

    public Book(int id, String name, String isbn, double price, String author, int publisherId, int mainCategoryId) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
        this.publisherId = publisherId;
        this.mainCategoryId = mainCategoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(int mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }
}