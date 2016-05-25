package com.ua.smarterama.andrey.leus.CRM.model;


import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.List;

public interface DataBaseManager {

    View view = null;

    void clear(String tableName) throws SQLException;

    void connect(String databaseName, String user, String password);

    void createDatabase(String databaseName) throws SQLException;

    void createTable(String query, Console view);

    void disconnectFromDatabase();

    void dropDatabase(String databaseName) throws SQLException;

    void dropTable(String tableName);

    List<String> getDatabases(Console view);

    public String selectTable(List<String> tables, Console view);

    List<String> getTableNames();

    void insert(String tableName, List<Object> list, Console view);

    void update(String tableName, List<Object> columnNames, int id, List<Object> list, Console view);

    boolean isConnected();

    List<Object> getTableData(String tableName, String query);

    List<Object> getColumnNames(String tableName, String query);

    void delete(int id, String taleName, Console view);

    String getFormatedLine(List<Object> listColumnName, List<Object> listValue);

    void outputData(List<Object> listColumnName, List<Object> listValue, String result, Console view);

    void outputColumnNames(List<Object> listColumnName, String result, Console view);
}

