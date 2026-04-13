package com.banana.game.service;

import com.banana.game.database.DatabaseConnection;

import java.sql.*;


public class UserService {

    /*
     * Register new user
     */
    public boolean register(String username, String password) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "INSERT INTO users (username, password, score) VALUES (?, ?, 0)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();

            return true;

        } catch (Exception e) {
            return false; // user already exists or error
        }
    }

    /*
     * Login user
     */
    public boolean login(String username, String password) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            // If record exists → login successful
            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Get user score
     */
    public int getScore(String username) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT score FROM users WHERE username=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("score");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * Update score in database
     */
    public void updateScore(String username, int score) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "UPDATE users SET score=? WHERE username=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, score);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}