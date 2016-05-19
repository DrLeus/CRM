package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

public class ConnectToDataBase extends Command {

    User user;
    static String initialNameDB = "CRM";
    static String initialUserName = "postgres";
    static String initialPass = "postgres";

    public ConnectToDataBase(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connect");
    }

    @Override
    public void process() {

            while(true){

                view.write("Желаете подключиться к текущей базе(CRM)? (Y/N)");

                String input = view.checkExit(view.read());

                if (input.equalsIgnoreCase("Y")){
                    user = new User(initialNameDB, initialUserName, initialPass);
                    manager.connect(user, view);
                    break;
                } else if (input.equalsIgnoreCase("N")) {
                    user = new User(null,null,null);
                    view.write("Введите имя базы");
                    user.database = view.checkExit(view.read());
                    view.write("Введите имя пользователя");
                    user.userName = view.checkExit(view.read());
                    view.write("Введите пароль");
                    user.password = view.checkExit(view.read());
                    manager.connect(user, view);
                    break;
                } else {
                    view.write("Oops... something wrong");
                }
            }
    }

    public class User {

        private String database ;
        private String userName;
        private String password;

        private User(String database, String userName, String password){
            this.database = database;
            this.userName = userName;
            this.password = password;
        }

        public String getNameDataBase(){
            return  database;
        }

        public String getUserName(){
            return  userName;
        }

        public String getPassword(){
            return  password;
        }

    }


}
