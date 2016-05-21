package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.command.ConnectToDataBase;
import com.ua.smarterama.andrey.leus.CRM.view.View;

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

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect(String databaseName, String user, String password) {

    }

    public void connect(ConnectToDataBase.User user, View view) {

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" +  user.getNameDataBase(), user.getUserName(),
                    user.getPassword()); //TODO change to abstract object
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
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String tableName, View view) {

        view.write("\nPlease input name of columns\n" +
                "The first column = 'id' with auto-increment");

        List <String> listColumn = inputNames(view);

        try  {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("CREATE SEQUENCE public." + tableName + "_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");

            stmt.executeUpdate("CREATE TABLE " + tableName +
                    "(id NUMERIC NOT NULL DEFAULT nextval('" + tableName + "_seq'::regclass), CONSTRAINT " + tableName + "_pkey PRIMARY KEY(id), " +
                    formatedLine(listColumn));
            view.write("The table " + tableName + " was created! Success!");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String formatedLine(List<String> listColumn) {
        String result = "";

        for (int i = 0; i <listColumn.size() ; i++) {
            result += listColumn.get(i) + " TEXT NOT NULL, ";
        }

        result = result.substring(0, result.length()-18) + ")";

        return result;
    }

    private List<String> inputNames(View view) {

        List<String> list = new ArrayList<>();

        String input = null;
        do {

            view.write("\nPlease input name for next column\n");

            input = view.checkExit(view.read());

            list.add(input);

        } while (!input.isEmpty());

        return list;
    }

    @Override
    public void disconnectFromDatabase() {

    }

    @Override
    public void dropDatabase(String databaseName) {


        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE public." + tableName + " CASCADE");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getDatabases(View view) {

        List <String> list = new ArrayList<>();

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
            list = null;
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<String> getTableNames() {

        List<String> list = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
            while (rs.next()) {
               list.add(rs.getString("table_name"));
            }
            rs.close();
            stmt.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            list = null;
            return list;
        }
    }

    @Override
    public void insert(String tableName, List<Object> list, View view) {

        List<Object> columnTable = getColumnNames(tableName);

        String columns = " (";
        for (int i = 1; i < columnTable.size(); i++) {
            columns += columnTable.get(i)+",";
        }
        columns = columns.substring(0, columns.length()-1) + ")";


        String data = " (";
        for (int i = 0; i < list.size(); i++) {
            data += "'" + list.get(i) + "',";
        }
        data = data.substring(0, data.length()-1) + ")";

        try  {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("INSERT INTO public." + tableName + columns +
                "VALUES " + data);
            view.write("\nThe row was created! Success!");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, List<Object> columnNames, int id, List<Object> list, View view) {


        for (int i = 1; i <columnNames.size(); i++) {


            String sql = "UPDATE " + tableName + " SET " + columnNames.get(i) +"='" + list.get(i-1) + "' WHERE id = " + id;

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        view.write("\nThe row was updated! Success!");

    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public List<Object> getTableData(String tableName) {
        List<Object> list = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName );

        ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()){
                for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                    list.add(rs.getObject(index));
                }
            }

        rs.close();
        stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Object> getColumnNames(String tableName) {
        List<Object> list = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);

            ResultSetMetaData rsmd = rs.getMetaData();

            for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                list.add(rsmd.getColumnName(index));
            }

        } catch (SQLException e) {        }

        return list;
    }

    @Override
    public void delete(int id, String tableName, View view) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName + " WHERE id=" + id );
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
