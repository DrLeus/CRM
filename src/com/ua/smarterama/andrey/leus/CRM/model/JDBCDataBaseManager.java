package com.ua.smarterama.andrey.leus.CRM.model;

import java.sql.*;
import java.util.List;
import java.util.Set;

public class JDBCDataBaseManager implements DataBaseManager {

    private Connection connection;
    private String user;
    private String nameDataBase;
    private String password;

    @Override
    public void clear(String tableName) {

    }

    @Override
    public void connect(String databaseName, String user, String password) {

    }

    @Override
    public void createDatabase(String databaseName) {

    }

    @Override
    public void createTable(String query) {

    }

    @Override
    public void disconnectFromDatabase() {

    }

    @Override
    public void dropDatabase(String databaseName) {

    }

    @Override
    public void dropTable(String tableName) {

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


    private int getAmountRowTable(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();

        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }



}
