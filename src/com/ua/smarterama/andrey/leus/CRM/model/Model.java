package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.MainController;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Admin on 28.04.2016.
 */
public interface Model {
    void catalog(Connection connection, View view, MainController controller) throws Exception;

    void list(Connection connection) throws SQLException, ClassNotFoundException;

    void add(Connection connection, View view);

    void delete(Connection connection, View view) throws SQLException, ClassNotFoundException;

    void update(Connection connection, View view) throws SQLException, ClassNotFoundException;
}