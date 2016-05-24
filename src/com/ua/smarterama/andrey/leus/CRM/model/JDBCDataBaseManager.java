package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.command.ConnectToDataBase;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.*;
import java.util.*;
import java.util.List;

public class JDBCDataBaseManager implements DataBaseManager {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oops.... Please add jdbc jar to project.", e);
        }
    }

    private Connection connection;

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
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + databaseName, user,
                    password);

        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Oops...Cant get connection for DB: %s; USER: %s; PASS: %s",
                            databaseName, user, password));
        }
    }

    @Override
    public String getFormatedLine(List<Object> listColumnName, List<Object> listValue) {
        String result = "";

        for (int i = 0; i < listColumnName.size(); i++) {
            result += "%-" + getWidthColumn(i, listColumnName, listValue) + "s";
        }
        result += "%n";
        return result;
    }

    private int getWidthColumn(int i, List<Object> listColumnName, List<Object> listValue) {

        int result = (String.valueOf(listColumnName.get(i))).length();

        int qtyLine = listValue.size()/listColumnName.size();

        for (int j = 0; j < qtyLine; j++) {
            if (result < (String.valueOf(listValue.get(i+(listColumnName.size()*j)))).length()) {
                result = (String.valueOf(listValue.get(i+(listColumnName.size()*j)))).length();            }
        }
        return result + 2;
    }

    @Override
    public void createDatabase(String databaseName) {

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String tableName, View view) {

        view.write("\nPlease input name of columns\n" +
                "The first column = 'id' with auto-increment");

        List <String> listColumn = inputNames(view);

        try (Statement stmt = connection.createStatement()) {

            stmt.executeUpdate("CREATE SEQUENCE public." + tableName + "_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");

            stmt.executeUpdate("CREATE TABLE " + tableName +
                    "(id NUMERIC NOT NULL DEFAULT nextval('" + tableName + "_seq'::regclass), CONSTRAINT " + tableName + "_pkey PRIMARY KEY(id), " +
                    formatedLine(listColumn));
            view.write("The table " + tableName + " was created! Success!");
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


        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            dropSequnce(tableName);
            stmt.executeUpdate("DROP TABLE public." + tableName + " CASCADE");
        } catch (SQLException e) {
            System.out.println("Error drop table");
            e.printStackTrace();
        }

    }

    private void dropSequnce(String tableName) {

        List<String> list = new ArrayList<>();

         try (Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT column_default FROM information_schema.columns WHERE table_name ='" + tableName + "'");)
         {
            rs.next();
                list.add(rs.getString("column_default"));
                if (list.get(0).contains(tableName)){
                    stmt.executeUpdate("DROP SEQUENCE public." + tableName + "_seq CASCADE");
                }
        } catch (SQLException e) {
            System.out.println("Error drop seq");
            e.printStackTrace();
        }


    }

    @Override
    public List<String> getDatabases(View view) {

        List <String> list = new ArrayList<>();

        try (PreparedStatement ps = connection
                .prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
             ResultSet rs = ps.executeQuery();)
        {
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

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");)
        {
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
    public void insert(String tableName, List<Object> list, View view) {

        List<Object> columnTable = getColumnNames(tableName, "");

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

        try ( Statement stmt = connection.createStatement();)  {
            stmt.executeUpdate("INSERT INTO public." + tableName + columns +
                "VALUES " + data);
            view.write("\nThe row was created! Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, List<Object> columnNames, int id, List<Object> list, View view) {

        for (int i = 1; i <columnNames.size(); i++) {

            String sql = "UPDATE " + tableName + " SET " + columnNames.get(i) +"='" + list.get(i-1) + "' WHERE id = " + id;

            try (PreparedStatement ps = connection.prepareStatement(sql);) {
                ps.executeUpdate();
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
    public List<Object> getTableData(String tableName, String query) {

        String sql;
        if (query.isEmpty()) {sql = "SELECT * FROM public." + tableName;}
        else {sql = query;}

            List<Object> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);)
        {
        ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()){
                for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                    list.add(rs.getObject(index));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Object> getColumnNames(String tableName, String query) {
        String sql;
        if (query.isEmpty()) {sql = "SELECT * FROM public." + tableName;}
        else {sql = query;}

        List<Object> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);)
        {
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                list.add(rsmd.getColumnName(index));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void delete(int id, String tableName, View view) {

        try (Statement stmt = connection.createStatement();) {
            stmt.executeUpdate("DELETE FROM public." + tableName + " WHERE id=" + id );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
