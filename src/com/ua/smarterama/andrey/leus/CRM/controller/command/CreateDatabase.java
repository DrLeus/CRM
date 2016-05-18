package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;


public class CreateDatabase extends Command {
    public CreateDatabase(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("create");
    }

    @Override
    public void process() {

                view.write("\nPlease input database name for creating:\n");

                String nameDataBase = view.checkExit(view.read());

        manager.createDatabase(nameDataBase);
        view.write("\nDatabse " + nameDataBase + " was created");
    }
}
