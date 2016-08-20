package ua.com.smart.andrey.leus.CRM.service;

import java.util.List;

public interface Service {

    List<String> commandsList();

    void connect(String databaseName, String userName, String password);
}
