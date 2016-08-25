package ua.com.smart.andrey.leus.CRM.service;

import java.util.List;
import java.util.Map;

public interface Service {

    List<String> commandsListMenu();

    List<String> commandsListCatalog();

    void connect(String databaseName, String userName, String password);

    void createDB(String databaseName);

    void dropDB(String databaseName);

    List<String> listDB();

    void createTable(String tableName, Map<String, String> listColumn);

    void clearTable(String tableName);

    List<String> getListTables();

    void removeTable(String tableName);

    List<String> getColumnNames(String tableName);

    List<Object> getTableData(String tableName);

    void insertData(String tableName, String value1, String value2, String value3 );
}
