package com.ua.smarterama.andrey.leus.CRM.model;


import com.ua.smarterama.andrey.leus.CRM.controller.command.ConnectToDataBase;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.util.List;

public interface DataBaseManager {

    View view = null;

    void clear(String tableName);

    void connect(String databaseName, String user, String password);

    void createDatabase(String databaseName);

    void createTable(String query, View view);

    void disconnectFromDatabase();

    void dropDatabase(String databaseName);

    void dropTable(String tableName);

    List<String> getDatabases(View view);

//    Set<String> getTableColumns(String tableName);

    List<String> getTableNames();

    void insert(String tableName, List<Object> list, View view);

    void update(String tableName, List<Object> columnNames, int id, List<Object> list, View view);

    boolean isConnected();

    List<Object> getTableData(String tableName, String query);

    List<Object> getColumnNames(String tableName, String query);

    void delete(int id, String taleName, View view);

    void connect(ConnectToDataBase.User user, View view);

    String getFormatedLine(List<Object> listColumnName, List<Object> listValue);
}
