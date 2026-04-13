package com.banana.game.database;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {

    // Database URL (database name: banana_game)
    private static final String URL = "jdbc:mysql://localhost:3306/banana_game";

    // Database username
    private static final String USER = "root";

    // Database password (empty in this case)
    private static final String PASSWORD = "";

    /*
     * Method to get database connection
     */
    public static Connection getConnection() throws Exception {

        // Connect to MySQL database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}