package org.example.util;

import org.example.repo.custom.BookRepo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object... args) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        if (sql.toLowerCase().startsWith("select")){
            ResultSet resultSet = ps.executeQuery();
            return (T) resultSet;
        }
        Boolean b = ps.executeUpdate() > 0;
        return (T) b;
    }
}
