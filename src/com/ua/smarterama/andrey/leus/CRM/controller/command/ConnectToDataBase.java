package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.*;

public class ConnectToDataBase extends Command {

    User user;
    static String initialNameDB = "CRM";
    static String initialUserName = "postgres";
    static String initialPass = "postgres";

    public ConnectToDataBase(View view, DataBaseManager manager) {
        super(view, manager);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connect");
    }

    @Override
    public void process() {

            while(true){

                view.write("Желаете подключиться к текущей базе(CRM)? (Y/N)");

                String input = view.read();

                if (input.equalsIgnoreCase("Y")){
                    user = new User(initialNameDB, initialUserName, initialPass);
                    connect();
                    break;
                } else if (input.equalsIgnoreCase("N")) {
                    user = new User(null,null,null);
                    view.write("Введите имя базы");
                    user.database = view.read();
                    view.write("Введите имя пользователя");
                    user.userName = view.read();
                    view.write("Введите пароль");
                    user.password = view.read();
                    connect();
                    break;
                } else {
                    view.write("упссс...... что-то не получилось");
                }
            }
    }

    void connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" +  user.getNameDataBase(), user.getUserName(),
                    user.getPassword());
            view.write("Connection succeeded to "+ user.database);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(
                    String.format("Cant get connection for DB:%s; user:%s; pass:%s",
                            user.getNameDataBase(), user.getUserName(), user.getPassword()),
                    e);
        }
    }

    private class User {

        private String database ;
        private String userName;
        private String password;

        private User(String database, String userName, String password){
            this.database = database;
            this.userName = userName;
            this.password = password;
        }

        private String getNameDataBase(){
            return  database;
        }

        private String getUserName (){
            return  userName;
        }

        private String getPassword(){
            return  password;
        }

    }


}
