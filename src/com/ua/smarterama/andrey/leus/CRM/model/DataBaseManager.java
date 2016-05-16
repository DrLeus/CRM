package com.ua.smarterama.andrey.leus.CRM.model;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataBaseManager {

    void clear(String tableName);

    void connect(String databaseName, String user, String password);

    void createDatabase(String databaseName);

    void createTable(String query);

    void disconnectFromDatabase();

    void dropDatabase(String databaseName);

    void dropTable(String tableName);

//    Set<String> getDatabases();

//    Set<String> getTableColumns(String tableName);

    List<Object> getTableData(String tableName) throws SQLException;

    Set<String> getTableNames();

    void insert(String tableName, Object input);

//    boolean isConnected();

    void update(String tableName, int id, Object newValue);

    }
