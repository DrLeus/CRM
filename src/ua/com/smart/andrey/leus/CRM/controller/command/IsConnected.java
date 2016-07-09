package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

import java.sql.SQLException;


public class IsConnected extends Command {
    public IsConnected(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) throws CRMException {

        try {
            return !manager.isConnected();
        } catch (CRMException e) {
            throw new CRMException("Error connection");
        }
    }

    @Override
    public void process() {
        view.write("Oops... Please connect to database!");
    }
}
