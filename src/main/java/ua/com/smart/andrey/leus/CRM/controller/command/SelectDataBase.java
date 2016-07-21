package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.List;

public class SelectDataBase extends Command {

    public SelectDataBase(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "list".equals(command);
    }

    @Override
    public void process() throws CRMException {
        List<String> list = manager.getDatabases();
        view.write("The next data bases available:\n");
        for (String nameDB : list) {
            view.write(nameDB);
        }
        view.write("\n");
    }
}
