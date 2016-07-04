package com.ua.smarterama.andrey.leus.CRM.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Configuration config = new Configuration();

    private static String currentDatabase = "";
    private static String currentUserName = "";
    private static String currentUserPassword = "";

    static {
        try {
            Class.forName(config.getClassDriver());
        } catch (ClassNotFoundException e) {
            try {
                System.out.println("Oops.... Try to add jdbc jar to project. \n");
                DriverManager.registerDriver(new org.postgresql.Driver());
                System.out.println("Success");
            } catch (SQLException e1) {
                try {
                    throw new SQLException("Couldn't register driver in case -", e1);
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public Connection getConnection() throws SQLException{
        try {

            String url = config.getDriver() + "://" + config.getServerName() + ":" + config.getPort() + "/" +
                    currentDatabase;

            return DriverManager.getConnection(url, currentUserName, currentUserPassword);
        } catch (SQLException e) {
            throw new SQLException("Couldn't get connection in case -", e);
        }
    }

    public static void currentDB(String databaseName, String user, String password) {
        currentDatabase = databaseName;
        currentUserName = user;
        currentUserPassword = password;
    }
}