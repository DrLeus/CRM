package ua.com.smart.andrey.leus.CRM.controller;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.controller.command.MainController;
import ua.com.smart.andrey.leus.CRM.controller.command.warehouse.MainControllerWarehouse;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;
import ua.com.smart.andrey.leus.CRM.view.View;

public class SelectedModule {
   private DataBaseManager manager;
    private View view;

    public SelectedModule(DataBaseManager manager, View view) {
       this.manager = manager;
        this.view = view;
    }

    public void makeChoice() throws Exception {


        while (true) {
            view.write("Do you want to initialize and than connect to database CRM for showing all abilities of module? (Y) " +
                    "or connect to your database (N)?;\n");

            String read = view.read();
            if ("Y".equalsIgnoreCase(read)) {
                MainControllerWarehouse controller = new MainControllerWarehouse(manager, view);
                view.write("Please wait!\n");
                controller.run(manager);
                break;
            } else if ("N".equalsIgnoreCase(read)) {
                MainController controller = new MainController(manager,view);
                controller.run();
                break;
            } else if ("exit".equalsIgnoreCase(read)) {
                System.exit(0);
            } else {
                view.write("Incorrect input, try again\n");
            }
        }
    }
}
