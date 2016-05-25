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

    void createTable(String query, List<String> listColumn) throws SQLException;

    void dropDatabase(String databaseName) throws SQLException;

    void dropTable(String tableName) throws SQLException;

    List<String> getDatabases(Console view);

    List<String> getTableNames();

    void insert(String tableName, String list, String data) throws SQLException;

    void update(String tableName, List<Object> columnNames, int id, List<Object> list) throws SQLException;

    boolean isConnected();

    List<Object> getTableData(String tableName, String query);

    List<Object> getColumnNames(String tableName, String query);

    void delete(int id, String taleName, Console view) throws SQLException;



}

