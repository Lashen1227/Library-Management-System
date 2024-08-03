package org.example.repo.custom.impl;

import org.example.entity.custom.Publisher;
import org.example.repo.custom.PublisherRepo;
import org.example.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherRepoIMPL implements PublisherRepo {
    @Override
    public Publisher save(Publisher publisher) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Publisher(name,location,contact) VALUES (?,?,?)";
        CrudUtil.execute(sql,publisher.getName(),publisher.getLocation(),publisher.getContact());
        return publisher;
    }

    @Override
    public boolean update(Publisher publisher) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Publisher SET name=?,location=?,contact=? WHERE id=?";
        return CrudUtil.execute(sql,publisher.getName(),publisher.getLocation(),publisher.getContact()
                ,publisher.getId());
    }

    @Override
    public Optional<Publisher> search(Integer s) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Publisher WHERE id= ?" ;
        ResultSet execute = CrudUtil.execute(sql,s);
        if (execute.next()) {
            Publisher publisher = new Publisher();
            publisher.setId(execute.getInt(1));
            publisher.setName(execute.getString(2));
            publisher.setLocation(execute.getString(3));
            publisher.setContact(execute.getString(4));
            return Optional.of(publisher);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer i) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Publisher WHERE id=?";
        return CrudUtil.execute(sql,i);
    }

    @Override
    public List<Publisher> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Publisher";
        ResultSet execute = CrudUtil.execute(sql);
        List<Publisher> publishers = new ArrayList<>();
        while (execute.next()){
            Publisher publisher = new Publisher();
            publisher.setId(execute.getInt(1));
            publisher.setName(execute.getString(2));
            publisher.setLocation(execute.getString(3));
            publisher.setContact(execute.getString(4));
            publishers.add(publisher);
        }
        return publishers;
    }
}