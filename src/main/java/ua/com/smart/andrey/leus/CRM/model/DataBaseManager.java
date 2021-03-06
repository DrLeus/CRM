package ua.com.smart.andrey.leus.CRM.model;

import java.util.List;
import java.util.Map;

public interface DataBaseManager {

    void connect(String databaseName, String user, String password) throws CRMException;

    void createDatabase(String databaseName) throws CRMException;

    void dropDatabase(String databaseName) throws CRMException;

    boolean isConnected() throws CRMException;


    void createTable(String tableName, Map<String,String> listColumn) throws CRMException;

    void dropTable(String tableName) throws CRMException;

    void clear(String tableName) throws CRMException;


    void insert(String tableName, List<String> columnTable, List<Object> value) throws CRMException;

    void update(String tableName, List<String> columnNames, int id, List<Object> list) throws CRMException;

    void delete(int id, String taleName) throws CRMException;


    List<String> getDatabases() throws CRMException;

    List<String> getTableNames() throws CRMException;

    List<Object> getTableData(String query) throws CRMException;

    List<String> getColumnNames(String query) throws CRMException;
}


