package com.ua.smarterama.andrey.leus.CRM.controller.command;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectDataBase extends Command {
    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" +  ConnectToDataBase.initialNameDB, ConnectToDataBase.initialUserName,
                    ConnectToDataBase.initialPass);//TODO fix this method
        } catch (SQLException e) {
            connection = null;
            System.out.println(e);
        }
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
            ResultSet rs = ps.executeQuery();
            view.write("The next data bases avaiilable:\n");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
