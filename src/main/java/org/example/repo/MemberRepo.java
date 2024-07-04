package org.example.repo;

import org.example.entity.Member;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRepo {
    public boolean saveMember(Member member) throws SQLException, ClassNotFoundException {
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


}
