package ua.com.smart.andrey.leus.CRM.controller.command.warehouse;

import ua.com.smart.andrey.leus.CRM.controller.command.*;
import ua.com.smart.andrey.leus.CRM.controller.command.tables.Catalog;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

public class MainControllerWarehouse {

    private Console view;
    private Command[] commands;

    public MainControllerWarehouse(DataBaseManager manager, Console view) {
        this.view = view;
        this.commands = new Command[]{
                new ConnectToDataBaseCRM(manager, view),
                new ConnectToDataBase(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new CreateDatabase(manager, view),
                new DropDataBase(manager, view),
                new Catalog(manager, view),
                new Report(manager, view),
                new SelectDataBase(manager, view),
                new Store(manager, view),
                new Writeoff(manager, view),
                new Unsuported(view),
        };
    }

    public void run(DataBaseManager manager) throws Exception {

        InitialDB_CRM.setupTempDates(manager);

        for (Command command : commands) {
            if (command.canProcess("connectCRM")) {
                command.process();
                break;
            }
        }

        view.write(Help.getHelp());
        InitialDB_CRM.getAddComands();

        try {
            doWork();
        } catch (ExitException e) {
            //do nothing
        }
    }

    public void doWork() throws Exception {

        while (true) {
            view.write("Please input command (or 'help'): \n");

            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process();
                        if ("help".equals(input)){
                            InitialDB_CRM.getAddComands();
                        }
                        break;
                    }
                } catch (ExitException e) {
                    throw new ExitException();
                }catch (Exception e) {
                    throw new Exception(e);
                }
            }
        }
    }
}