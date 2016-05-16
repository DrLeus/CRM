package com.ua.smarterama.andrey.leus.CRM.model;

import java.util.List;
import java.util.Set;

public class JDBCDataBaseManager implements DataBaseManager {
    @Override
    public void clear(String tableName) {

    }

    @Override
    public void connect(String databaseName, String user, String password) {

    }

    @Override
    public void createDatabase(String databaseName) {

    }

    @Override
    public void createTable(String query) {

    }

    @Override
    public void disconnectFromDatabase() {

    }

    @Override
    public void dropDatabase(String databaseName) {

    }

    @Override
    public void dropTable(String tableName) {

    }

    @Override
    public List<Object> getTableData(String tableName) {
        return null;
    }

    @Override
    public Set<String> getTableNames() {
        return null;
    }

    @Override
    public void insert(String tableName, Object input) {

    }

    @Override
    public void update(String tableName, int id, Object newValue) {

    }
}
