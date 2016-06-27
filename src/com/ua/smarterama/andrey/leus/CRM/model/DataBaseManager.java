package com.ua.smarterama.andrey.leus.CRM.model;

import java.sql.SQLException;
import java.util.List;

public interface DataBaseManager {

    void clear(String tableName) throws SQLException;

    void connect(String databaseName, String user, String password) throws SQLException;

    void createDatabase(String databaseName) throws SQLException;

    void createTable(String tableName, List<Object> listColumn) throws SQLException;

    void dropDatabase(String databaseName) throws SQLException;

    void dropTable(String tableName) throws SQLException;

    List<String> getDatabases() ;

    List<String> getTableNames() ;

    void insert(String tableName, List<Object> columnTable, List<Object> value) throws SQLException;

    void update(String tableName, List<Object> columnNames, int id, List<Object> list) throws SQLException;

    boolean isConnected();

    List<Object> getTableData(String tableName, String query) throws SQLException;

    List<Object> getColumnNames(String tableName, String query) throws SQLException;

    void delete(int id, String taleName) throws SQLException;

    void disconnectFromDataBase() throws SQLException;
}


