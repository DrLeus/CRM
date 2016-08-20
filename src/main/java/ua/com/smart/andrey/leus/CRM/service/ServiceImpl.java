package ua.com.smart.andrey.leus.CRM.service;


import java.util.Arrays;
import java.util.List;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;

/**
 * Created by oleksandr.baglai on 30.10.2015.
 */
public class ServiceImpl implements Service {

    private DataBaseManager manager;

    public ServiceImpl() {
        manager = new JDBCDataBaseManager();
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "menu", "connect");
    }

    @Override
    public void connect(String databaseName, String userName, String password) {
        manager.connect(databaseName, userName, password);
    }

}
