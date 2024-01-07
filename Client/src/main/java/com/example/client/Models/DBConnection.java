package com.example.client.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String link = "jdbc:mysql://localhost:3306/test";
    private static  Connection connector;
    public static Connection getConnection(){

        try {
            connector = DriverManager.getConnection(link , "root", "Dola123@");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connector;
    }

}