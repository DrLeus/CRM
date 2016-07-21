package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;


public class ConnectToDataBase extends Command {

    public ConnectToDataBase(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "connect".equals(command);
    }

    @Override
    public void process() throws CRMException {

        String nameDB;
        String userName;
        String password;

        while (true) {
            view.write("Please input the database name");
            nameDB = view.read();
            if (checkExit(nameDB)) {
                view.write("Return to main menu!\n");
                return;
            }

            view.write("Please input user name");
            userName = view.read();
            if (checkExit(userName)) {
                view.write("Return to main menu!\n");
                return;
            }

            view.write("Please input password");
            password = view.read();
            if (checkExit(password)) {
                view.write("Return to main menu!\n");
                return;
            }
            try {
                manager.connect(nameDB, userName, password);
                view.write("\n");
                view.write(String.format("Connection succeeded to '%s'%n", nameDB));
                break;
            } catch (CRMException e) {
                view.write(String.format("Oops...Cant get connection for DB: %s; USER: %s; PASS: %s%n",
                        nameDB, userName, password));
            }
        }
    }
}