package com.ua.smarterama.andrey.leus.CRM.model;


import com.ua.smarterama.andrey.leus.CRM.controller.command.ConnectToDataBase;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DataBaseManager {

    View view = null;

    void clear(String tableName);

    void connect(String databaseName, String user, String password);

    void connect(ConnectToDataBase.User user, View view);

    void createDatabase(String databaseName);

    void createTable(String query);

    void disconnectFromDatabase();

    void dropDatabase(String databaseName);

    void dropTable(String tableName);

    List<String> getDatabases(View view);

//    Set<String> getTableColumns(String tableName);

    List<Object> getTableData(String tableName) throws SQLException;

    Set<String> getTableNames();

    void insert(String tableName, Object input);

//    boolean isConnected();

    void update(String tableName, int id, Object newValue);

    boolean isConnected();

}
