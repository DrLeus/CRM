package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.*;
import java.util.*;
import java.util.List;

public class JDBCDatabaseManager implements DataBaseManager {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oops.... Please add jdbc jar to project.", e);
        }
    }

    private Connection connection;

    @Override
    public void clear(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + tableName);
        }
    }

    @Override
    public void connect(String databaseName, String user, String password) throws SQLException {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + databaseName, user,
                    password);

    }

    @Override
    public void createDatabase(String databaseName) throws SQLException {

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE " + databaseName);
        }
    }

    @Override
    public void createTable(String tableName, List<Object> listColumn) throws SQLException {

        try (Statement stmt = connection.createStatement()) {

            createSequence(tableName, stmt);

            String sql = "CREATE TABLE " + tableName +
                    "(id NUMERIC NOT NULL DEFAULT nextval('" + tableName + "_seq'::regclass), CONSTRAINT " + tableName + "_pkey PRIMARY KEY(id), " +
                    getFormatedLine(listColumn);
            stmt.executeUpdate(sql);
        }
    }

    public void createSequence(String tableName, Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE SEQUENCE public." + tableName + "_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");
    }

    private String getFormatedLine(List<Object> listColumn) {
        String result = "";

        for (int i = 0; i < listColumn.size(); i++) {
            result += listColumn.get(i) + " TEXT NOT NULL, ";
        }

        result = result.substring(0, result.length() - 18) + ")";

        return result;
    }

    @Override
    public void dropDatabase(String databaseName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
        }
    }

    @Override
    public void dropTable(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            dropSequnce(tableName);
            stmt.executeUpdate("DROP TABLE public." + tableName + " CASCADE");
        }
    }

    private void dropSequnce(String tableName) {

        List<String> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT column_default FROM information_schema.columns WHERE table_name ='" + tableName + "'");) {
            rs.next();
            list.add(rs.getString("column_default"));

            if (list.get(0).contains(tableName)) {
                stmt.executeUpdate("DROP SEQUENCE public." + tableName + "_seq CASCADE");
            }
        } catch (SQLException e) {
            System.out.println("Error drop seq");
            e.printStackTrace();
        }


    }

    @Override
    public List<String> getDatabases() {

        List<String> list = new ArrayList<>();

        try (PreparedStatement ps = connection
                .prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
             ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            list = null;
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public List<String> getTableNames() {

        List<String> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");) {
            while (rs.next()) {
                list.add(rs.getString("table_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            list = null;
            return list;
        }
        return list;
    }

    @Override
    public void insert(String tableName,  List<Object> columnTable, List<Object> value) throws SQLException {


        String columns = " (";
        for (int i =1 ; i < columnTable.size(); i++) {
            columns += columnTable.get(i)+",";
        }
        columns = columns.substring(0, columns.length()-1) + ")";


        String data = " (";
        for (int i = 0; i < value.size(); i++) {
            data += "'" + value.get(i) + "',";
        }
        data = data.substring(0, data.length()-1) + ")";

        try (Statement stmt = connection.createStatement();) {
            stmt.executeUpdate("INSERT INTO public." + tableName + columns +
                    "VALUES " + data);
        }
    }

    @Override
    public void update(String tableName, List<Object> columnNames, int id, List<Object> list) throws SQLException {

        for (int i = 1; i < columnNames.size(); i++) {

            String sql = "UPDATE " + tableName + " SET " + columnNames.get(i) + "='" + list.get(i-1) + "' WHERE id = " + id;

            try (PreparedStatement ps = connection.prepareStatement(sql);) {
                ps.executeUpdate();
            }
        }

    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public List<Object> getTableData(String tableName, String query) throws SQLException {

        String sql;
        if (query.isEmpty()) {
            sql = "SELECT * FROM public." + tableName;
        } else {
            sql = query;
        }

        List<Object> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                    list.add(rs.getObject(index));
                }
            }
        }
        return list;
    }

    @Override
    public List<Object> getColumnNames(String tableName, String query) throws SQLException {
        String sql;
        if (query.isEmpty()) {
            sql = "SELECT * FROM public." + tableName;
        } else {
            sql = query;
        }

        List<Object> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                list.add(rsmd.getColumnName(index));
            }
    }
        return list;
    }

    @Override
    public void delete(int id, String tableName) throws SQLException {

        try (Statement stmt = connection.createStatement();) {
            stmt.executeUpdate("DELETE FROM public." + tableName + " WHERE id=" + id);
        }
    }

}
