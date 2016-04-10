package com.ua.smarterama.andrey.leus.CRM.controller;


import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) {

        MainController controller = new MainController();
        try {
            controller.run();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
