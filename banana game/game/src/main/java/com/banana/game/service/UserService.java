package com.banana.game.service;

import com.banana.game.database.DatabaseConnection;

import java.sql.*;

public class UserService {

    public boolean register(String username, String password) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "INSERT INTO users (username, password, score) VALUES (?, ?, 0)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
