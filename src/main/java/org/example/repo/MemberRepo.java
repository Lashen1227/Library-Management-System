package org.example.repo;

import org.example.entity.Member;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MemberRepo {

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

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM member WHERE id = ?");
        ps.setString(1,id);
        int affectedRows = ps.executeUpdate();
        return affectedRows> 0;
    }

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

    public Optional<Member> searchCustomer(String customerId) throws SQLException, ClassNotFoundException {
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


}
