package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;


public class CreateDatabase extends Command {

    public CreateDatabase(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "create".equals(command);
    }

    @Override
    public void process() throws CRMException {

        view.write("Please input database name for creating:\n");

        String nameDataBase = view.read();

        if (checkExit(nameDataBase)) {
            view.write("Return to main menu!\n");
            return;
        }

        try {
            manager.createDatabase(nameDataBase);
            view.write(String.format("Database %s was created%n", nameDataBase));
        } catch (CRMException e) {
            throw new CRMException(String.format("Create database, error in case - %s%n", e));
        }
    }
}
