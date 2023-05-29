package com.example.testserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDbConnection {
    public static void main(String[] args) throws SQLException {
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost/3306/test","root","Pranay@5");
        System.out.println("Connection Success!");
    }
}
