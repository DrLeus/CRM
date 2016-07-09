package ua.com.smart.andrey.leus.CRM.controller.command.warehouse;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.Configuration;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

public class ConnectToDataBaseCRM extends Command {

    Configuration config = new Configuration();


    public ConnectToDataBaseCRM(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "connectCRM".equals(command);
    }

    @Override
    public void process() throws CRMException {

        try {
            manager.connect(config.getDatabaseNameCRM(), config.getUserName(), config.getUserPassword());
            view.write(String.format("Connection succeeded to %s%n", config.getDatabaseNameCRM()));
        } catch (CRMException e) {
            throw new CRMException("Oops...Cant get connection to current database  in case " + e);
        }
    }
}


