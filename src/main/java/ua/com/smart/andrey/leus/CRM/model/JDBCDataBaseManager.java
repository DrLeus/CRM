package ua.com.smart.andrey.leus.CRM.model;

import java.sql.*;
import java.util.*;
import java.util.List;

public class JDBCDataBaseManager implements DataBaseManager {

    public static final String ERROR_CONNECTION_CLOSE = "Error connection close in case - %s\n";
    private final DatabaseConnection databaseConnection = new DatabaseConnection() {
    };

    @Override
    public void clear(String tableName) throws CRMException {
        executeUpdate("DELETE FROM " + tableName);
    }

    @Override
    public void connect(String databaseName, String user, String password) throws CRMException {
        DatabaseConnection.currentDB(databaseName, user, password);

        // check connection correctly
        isConnected();
    }

    @Override
    public void createDatabase(String databaseName) throws CRMException {
        executeUpdate("CREATE DATABASE " + databaseName);
    }

    @Override
    public void createTable(String tableName, Map<String, String> columns) throws CRMException {

        dropSequnce(tableName); //TODO delete
        dropTable(tableName);//TODO delete
        createSequence(tableName);

        String sql = String.format("CREATE TABLE %s(id NUMERIC NOT NULL DEFAULT nextval('%s_seq'::regclass), CONSTRAINT " +
                "%s_pkey PRIMARY KEY(id), %s", tableName, tableName, tableName, getFormatedLine(columns));
        executeUpdate(sql);
    }

    private void createSequence(String tableName) throws CRMException {
        executeUpdate("CREATE SEQUENCE public." + tableName + "_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");
    }

    private String getFormatedLine(Map<String, String> columns) {

        String result = "";
        for (Map.Entry<String, String> pair : columns.entrySet()) {
            result += String.format(" %s %s NOT NULL,", pair.getKey(), pair.getValue());
        }
        result = result.substring(0, result.length()-1) + ")";
        return result;
    }

    @Override
    public void dropDatabase(String databaseName) throws CRMException {
        executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
    }

    @Override
    public void update(String tableName, List<Object> columnNames, int id, List<Object> list) throws CRMException {

        for (int i = 1; i < columnNames.size(); i++) {

            String sql = String.format("UPDATE %s SET %s='%s' WHERE id = %s", tableName, columnNames.get(i), list.get(i - 1), id);

            executeUpdate(sql);
        }
    }

    @Override
    public void delete(int id, String tableName) throws CRMException {
        executeUpdate(String.format("DELETE FROM public.%s WHERE id=%s", tableName, id));
    }

    @Override
    public void insert(String tableName, List<Object> columnTable, List<Object> value) throws CRMException {

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
    public void dropTable(String tableName) throws CRMException {
        dropSequnce(tableName);
        executeUpdate(String.format("DROP TABLE IF EXISTS public.%s CASCADE", tableName));
    }

    private void dropSequnce(String tableName) throws CRMException {

        try {
            executeUpdate(String.format("DROP SEQUENCE IF EXISTS public.%s_seq CASCADE", tableName));
        } catch (CRMException e) {
            throw new CRMException("Error drop seq in case:" + e.getMessage());
        }
    }

    @Override
    public List<String> getDatabases() throws CRMException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<String> list = new ArrayList<>();

        String sql = "SELECT datname FROM pg_database WHERE datistemplate = false;";
        try {
            connection = databaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException e) {
            throw new CRMException("Error get result set in case - %s\n", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new CRMException(ERROR_CONNECTION_CLOSE, e);
            }
        }
    }

    @Override
    public List<String> getTableNames() throws CRMException {

        List<String> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";

        try {
            connection = databaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("table_name"));
            }
            return list;
        } catch (SQLException e) {
            throw new CRMException("Error get result set in case - %s\n", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new CRMException(ERROR_CONNECTION_CLOSE, e);
            }
        }
    }

    @Override
    public boolean isConnected() throws CRMException {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
            return true;
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new CRMException(ERROR_CONNECTION_CLOSE, e);
            }
        }
    }

    @Override
    public List<Object> getTableData(String query) throws CRMException {

        String sql;
        if (!query.startsWith("SELECT")) {
            sql = String.format("SELECT * FROM public.%s ORDER BY id", query);
        } else {
            sql = query + " ORDER BY id";
        }

        List<Object> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = databaseConnection.getConnection();
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
            throw new CRMException("Error get data in case - %s\n", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new CRMException(ERROR_CONNECTION_CLOSE, e);
            }
        }
    }

    @Override
    public List<Object> getColumnNames(String query) throws CRMException {

        String sql;
        if (!query.startsWith("SELECT")) {
            sql = String.format("SELECT * FROM public.%s", query);
        } else {
            sql = query;
        }

        List<Object> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = databaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                list.add(rsmd.getColumnName(index));
            }
            return list;

        } catch (SQLException e) {
            throw new CRMException("Error update data in case - %s\n", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new CRMException(ERROR_CONNECTION_CLOSE, e);
            }
        }
    }

    private void executeUpdate(String sql) throws CRMException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = databaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CRMException("Error update data in case - %s\n", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new CRMException(ERROR_CONNECTION_CLOSE, e);
            }
        }
    }
}

