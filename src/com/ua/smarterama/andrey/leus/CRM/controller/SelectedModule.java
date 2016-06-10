package com.ua.smarterama.andrey.leus.CRM.controller;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.controller.command.MainController;
import com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse.MainControllerWarehouse;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

/**
 * Created by Admin on 08.06.2016.
 */
public class SelectedModule extends Command {


    private DataBaseManager manager;

    public SelectedModule(DataBaseManager manager) {
        this.manager = manager;
    }

    public void makeChoice() throws Exception {


        while (true) {
            view.write("Do you want to initialize and than connect to database CRM for showing all abilities of module? (Y) " +
                    "or connect to your database (N)?;\n");

            String read = view.read();
            if (read.equalsIgnoreCase("Y")) {
                MainControllerWarehouse controller = new MainControllerWarehouse(manager);
                view.write("Please wait!\n");
                controller.run(manager);
                break;
            } else if (read.equalsIgnoreCase("N")) {
                MainController controller = new MainController(manager);
                controller.run();
                break;
            } else if (read.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else {
                view.write("Incorrect input, try again\n");
            }
        }
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}
}
