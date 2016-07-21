package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;


public class IsConnected extends Command {
    public IsConnected(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {

        try {
            return !manager.isConnected();
        } catch (CRMException e) {
            // nothing to do
        }
        return true;
    }

    @Override
    public void process() {
        view.write("Oops... Please connect to database!\n");
    }
}
