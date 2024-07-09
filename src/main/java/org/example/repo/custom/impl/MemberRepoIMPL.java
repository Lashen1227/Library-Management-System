package org.example.repo.custom.impl;

import org.example.entity.custom.Member;
import org.example.repo.custom.MemberRepo;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepoIMPL implements MemberRepo {

    @Override
    public boolean save(Member member) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO member(id,name,address,email,contact) VALUES(?,?,?,?,?)");
        ps.setString(1, member.getId());
        ps.setString(2, member.getName());
        ps.setString(3, member.getAddress());
        ps.setString(4, member.getEmail());
        ps.setString(5, member.getContact());
        int affectedRows = ps.executeUpdate();
        return affectedRows> 0;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM member WHERE id = ?");
        ps.setString(1,id);
        int affectedRows = ps.executeUpdate();
        return affectedRows> 0;
    }

    @Override
    public boolean update(Member member) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE member set name = ? , address = ? , email = ? , contact = ? where id = ?");
        ps.setString(1,member.getName());
        ps.setString(2, member.getAddress());
        ps.setString(3, member.getEmail());
        ps.setString(4, member.getContact());
        ps.setString(5, member.getId());
        return ps.executeUpdate()>0;
    }

    @Override
    public Optional<Member> search(String customerId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM member WHERE id = ?");
        ps.setString(1,customerId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String email = rs.getString(4);
            String contact = rs.getString(5);
            Member ob = new Member(id,name,address,email,contact);
            return Optional.of(ob);
        }
        return Optional.empty();
    }

    @Override
    public List<Member> getAll() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM member");
        ResultSet rs = ps.executeQuery();
        List<Member> list = new ArrayList<>();
        while(rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String email = rs.getString(4);
            String contact = rs.getString(5);
            Member member = new Member(id,name,address,email,contact);
            list.add(member);
        }
        return list;
    }
}