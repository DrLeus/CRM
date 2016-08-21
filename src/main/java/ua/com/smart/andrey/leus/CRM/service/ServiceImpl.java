package ua.com.smart.andrey.leus.CRM.service;


import java.util.Arrays;
import java.util.List;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;

public class ServiceImpl implements Service {

    private DataBaseManager manager;

    public ServiceImpl() {
        manager = new JDBCDataBaseManager();
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "menu", "connect", "selectDB", "createDB", "dropDB");
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
    public  List<String> selectDB() {
       return manager.getDatabases();
    }

}
