package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.command.ConnectToDataBase;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class JDBCDataBaseManager implements DataBaseManager {

    private Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oops.... Please add jdbc jar to project.", e);
        }
    }


    @Override
    public void clear(String tableName) {

    }

    @Override
    public void connect(String databaseName, String user, String password) {

    }

    @Override
    public void connect(ConnectToDataBase.User user, View view) {

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" +  user.getNameDataBase(), user.getUserName(),
                    user.getPassword());
            view.write("Connection succeeded to "+ user.getNameDataBase());
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Oops...Cant get connection for DB: %s; USER: %s; PASS: %s",
                            user.getNameDataBase(), user.getUserName(), user.getPassword()));
        }

    }

    @Override
    public void createDatabase(String databaseName) {

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String query) {

    }

    @Override
    public void disconnectFromDatabase() {

    }

    @Override
    public void dropDatabase(String databaseName) {


        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropTable(String tableName) {

    }

    @Override
    public List<String> getDatabases(View view) {

        List <String> list = new ArrayList<String>();

        String nameDataBase = "CRM";
        String userName = "postgres";
        String password = "postgres";

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" +  nameDataBase, userName,
                    password);//TODO fix this method
        } catch (SQLException e) {
            connection = null;
            System.out.println("Ошибка 54");
        }
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Ошибка 764");
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Object> getTableData(String tableName) throws SQLException {
        int size = getAmountRowTable(tableName);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] result = new String[size];
        int index = 0;
        while (rs.next()) {
            String str = "\n";
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                str += String.format("%-16s", rs.getObject(i));
                str += "\t\t";
            }
            result[index] = str;
            index++;
        }
        rs.close();
        stmt.close();

        return null;
    }

    @Override
    public Set<String> getTableNames() {
        return null;
    }

    @Override
    public void insert(String tableName, Object input) {

    }

    @Override
    public void update(String tableName, int id, Object newValue) {

    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }


    private int getAmountRowTable(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();

        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }



}
