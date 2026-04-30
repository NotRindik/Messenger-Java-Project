package com.example.messengermaxsat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;

@Service
public class DatabaseManager {
    @Autowired
    private DataSource dataSource;

    public void saveMessage(String sender, String content) {
        String sql = "INSERT INTO messages (sender, content) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sender);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
            System.out.println("Сообщение сохранено в Postgres!");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}