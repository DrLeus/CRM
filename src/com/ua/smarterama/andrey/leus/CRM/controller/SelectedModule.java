package com.ua.smarterama.andrey.leus.CRM.controller;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.controller.command.MainController;
import com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse.MainControllerWarehouse;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public void checkCMD() throws IOException {

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "sc query postgresql-x64-9.4");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
    }

    /*надо распарсить ответ
    сервисы могут быть postgresql-x64-9.x
     перебрать несколько возможных
     порт другой командой вычисляется
     затем результат подставить в коннекшин стринг*/

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}
}
