package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

public class ConnectToDataBase extends Command {

    final static String initialNameDB = "CRM";
    final static String initialUserName = "postgres";
    final static String initialPass = "postgres";

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
                    manager.connect(initialNameDB, initialUserName, initialPass);
                    view.write("Connection succeeded to " + initialNameDB);
                    break;
                } else if (input.equalsIgnoreCase("N")) {
                    view.write("Введите имя базы");
                    String nameDB = view.checkExit(view.read());
                    view.write("Введите имя пользователя");
                    String userName = view.checkExit(view.read());
                    view.write("Введите пароль");
                    String password = view.checkExit(view.read());
                    manager.connect(nameDB, userName, password);
                    view.write("Connection succeeded to " + initialNameDB);
                    break;
                } else {
                    view.write("Oops... something wrong");
                }
            }
    }
}
