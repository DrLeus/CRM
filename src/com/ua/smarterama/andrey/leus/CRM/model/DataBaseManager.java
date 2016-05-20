package com.ua.smarterama.andrey.leus.CRM.model;


import com.ua.smarterama.andrey.leus.CRM.controller.command.ConnectToDataBase;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.util.List;

public interface DataBaseManager {

    View view = null;

    void clear(String tableName);

    void connect(String databaseName, String user, String password);

    void connect(ConnectToDataBase.User user, View view);

    void createDatabase(String databaseName);

    void createTable(String query, View view);

    void disconnectFromDatabase();

    void dropDatabase(String databaseName);

    void dropTable(String tableName);

    List<String> getDatabases(View view);

//    Set<String> getTableColumns(String tableName);

    List<String> getTableNames();

    void insert(String tableName, Object input);

    void update(String tableName, int id, Object newValue);

    boolean isConnected();

    List<Object> getTableData(String tableName);

    List<Object> getColumnNames(String tableName);

}
