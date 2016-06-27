package com.ua.smarterama.andrey.leus.CRM.model;

import java.sql.*;
import java.util.*;
import java.util.List;

public class JDBCDataBaseManager implements DataBaseManager {

    private final DaoFactory daoFactory = new DaoFactory() {
    };

    @Override
    public void clear(String tableName) throws SQLException {
        executeUpdate("DELETE FROM " + tableName);
    }

    private void executeUpdate(String sql) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
//        Statement stmt = null;

        try {
            connection = daoFactory.getConnection();
            ps = connection.prepareStatement(sql);
//            stmt = connection.createStatement();
//            stmt.executeUpdate(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error update data in case - %s\n", e);
        } finally {
            try {
//                if (stmt != null) stmt.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new SQLException("Error connection close in case - %s\n", e);
            }
        }
    }

    @Override
    public void connect(String databaseName, String user, String password) throws SQLException {
        DaoFactory.currentDB(databaseName, user, password);

        // checking connection
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new SQLException("Error connection close in case - %s\n", e);
            }
        }
    }

    @Override
    public void createDatabase(String databaseName) throws SQLException {
        executeUpdate("CREATE DATABASE " + databaseName);
    }

    @Override
    public void createTable(String tableName, List<Object> listColumn) throws SQLException {

        createSequence(tableName);

        String sql = String.format("CREATE TABLE %s(id NUMERIC NOT NULL DEFAULT nextval('%s_seq'::regclass), CONSTRAINT " +
                "%s_pkey PRIMARY KEY(id), %s", tableName, tableName, tableName, getFormatedLine(listColumn));
        executeUpdate(sql);
    }

    private void createSequence(String tableName) throws SQLException {
        executeUpdate("CREATE SEQUENCE public." + tableName + "_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");
    }

    private String getFormatedLine(List<Object> listColumn) {
        String result = "";

        for (int i = 0; i < listColumn.size(); i++) {
            result += listColumn.get(i) + " NOT NULL, ";
        }

        result = result.substring(0, result.length() - 13) + ")";

        return result;
    }

    @Override
    public void dropDatabase(String databaseName) throws SQLException {
        executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
    }

    @Override
    public void update(String tableName, List<Object> columnNames, int id, List<Object> list) throws SQLException {

        for (int i = 1; i < columnNames.size(); i++) {

            String sql = String.format("UPDATE %s SET %s='%s' WHERE id = %s", tableName, columnNames.get(i), list.get(i - 1), id);

            executeUpdate(sql);
        }
    }

    @Override
    public void delete(int id, String tableName) throws SQLException {
        executeUpdate(String.format("DELETE FROM public.%s WHERE id=%s", tableName, id));
    }

    @Override
    public void insert(String tableName, List<Object> columnTable, List<Object> value) throws SQLException {

        String columns = " (";
        for (int i = 1; i < columnTable.size(); i++) {
            columns += columnTable.get(i) + ",";
        }
        columns = columns.substring(0, columns.length() - 1) + ")";

        String data = " (";
        for (int i = 0; i < value.size(); i++) {
            data += "'" + value.get(i) + "',";
        }
        data = data.substring(0, data.length() - 1) + ")";

        String sql = String.format("INSERT INTO public.%s %s VALUES %s", tableName, columns, data);
        executeUpdate(sql);
    }

    @Override
    public void dropTable(String tableName) throws SQLException {
        dropSequnce(tableName);
        executeUpdate(String.format("DROP TABLE IF EXISTS public.%s CASCADE", tableName));
    }

    private void dropSequnce(String tableName) {

        try {
            executeUpdate(String.format("DROP SEQUENCE IF EXISTS public.%s_seq CASCADE", tableName));
        } catch (SQLException e) {
            System.out.println("Error drop seq in case:" + e.getMessage());
        }
    }

    @Override
    public List<String> getDatabases() {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<String> list = new ArrayList<>();

        String sql = "SELECT datname FROM pg_database WHERE datistemplate = false;";
        try {
            connection = daoFactory.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            list = null;
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                try {
                    throw new SQLException("Error connection close in case - %s\n", e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getTableNames() {

        List<String> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";

        try {
            connection = daoFactory.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("table_name"));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                try {
                    throw new SQLException("Error connection close in case - %s\n", e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean isConnected() {
//        return connection != null;
        return true;
    }

    @Override
    public List<Object> getTableData(String tableName, String query) throws SQLException {

        String sql;
        if (query.isEmpty()) {
            sql = String.format("SELECT * FROM public.%s ORDER BY id", tableName);
        } else {
            sql = query + " ORDER BY id";
        }

        List<Object> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = daoFactory.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                    list.add(rs.getObject(index));
                }
            }
            return list;

        } catch (SQLException e) {
            try {
                throw new SQLException("Error get data in case - %s\n", e);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                try {
                    throw new SQLException("Error connection close in case - %s\n", e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public List<Object> getColumnNames(String tableName, String query) throws SQLException {
        String sql;
        if (query.isEmpty()) {
            sql = String.format("SELECT * FROM public.%s", tableName);
        } else {
            sql = query;
        }

        List<Object> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = daoFactory.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                list.add(rsmd.getColumnName(index));
            }
            return list;

        } catch (SQLException e) {
            try {
                throw new SQLException("Error update data in case - %s\n", e);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                try {
                    throw new SQLException("Error connection close in case - %s\n", e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }
}

