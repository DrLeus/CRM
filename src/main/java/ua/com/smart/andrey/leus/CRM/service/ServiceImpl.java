package ua.com.smart.andrey.leus.CRM.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;

public class ServiceImpl implements Service {

    private DataBaseManager manager;

    public ServiceImpl() {
        manager = new JDBCDataBaseManager();
    }

    @Override
    public List<String> commandsListMenu() {
        return Arrays.asList("help", "menu", "connect", "listDB", "createDB", "dropDB", "exit", "catalog");
    }

    @Override
    public List<String> commandsListCatalog() {
        return Arrays.asList("createTable", "clearTable", "removeTable", "getTable", "insertData", "updateData", "mainMenu");
    }

    @Override
    public void connect(String databaseName, String userName, String password) {
        manager.connect(databaseName, userName, password);
    }

    @Override
    public void createDB(String databaseName) {
        manager.createDatabase(databaseName);
    }

    @Override
    public void dropDB(String databaseName) {
        manager.dropDatabase(databaseName);
    }

    @Override
    public  List<String> listDB() {
       return manager.getDatabases();
    }

    @Override
    public void createTable(String tableName, Map<String, String> columns) {
        manager.createTable(tableName, columns);
    }

    @Override
    public void clearTable(String tableName) {
        manager.clear(tableName);
    }

    @Override
    public List<String> getListTables() {
        return manager.getTableNames();
    }

    @Override
    public void removeTable(String tableName) {
        manager.dropTable(tableName);
    }

    @Override
    public List<String> getColumnNames(String tableName) {
        return manager.getColumnNames(tableName);
    }

    @Override
    public List<Object> getTableData(String tableName) {
        return manager.getTableData(tableName);
    }

    @Override
    public void insertData(String tableName, String value1, String value2, String value3 ) {
        List<String> columnTable = manager.getColumnNames(tableName);
        List<Object> value = new ArrayList<>();
        value.add(value1);
        value.add(value2);
        value.add(value3);
        manager.insert(tableName, columnTable, value);
    }
}
