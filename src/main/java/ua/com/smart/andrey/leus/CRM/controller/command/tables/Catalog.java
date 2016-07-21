package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.*;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.HashMap;
import java.util.Map;

public class Catalog extends Command {

    public Catalog(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "catalog".equals(command);
    }

    @Override
    public void process() throws CRMException {

        Map<Integer, Command> commands = new HashMap<>();
        commands.put(1, new GetTable(manager, view));
        commands.put(2, new InsertData(manager, view));
        commands.put(3, new UpdateDate(manager, view));
        commands.put(4, new DeleteData(manager, view));
        commands.put(5, new CreateTable(manager, view));
        commands.put(6, new RemoveTable(manager, view));
        commands.put(7, new ClearTable(manager, view));

        while (true) {
            try {
                view.write("Available operations:\n");
                view.write(cutSymbols(commands.values().toString()));
                view.write(" 8. Return to main menu\n");
                view.write("Please select operation:\n");

                String input = view.read();
                if (checkExit(input)) {
                    view.write("Return to main menu!\n");
                    return;
                }

                int i = Integer.parseInt(input);
                if (i == 8) {
                    view.write("Return to main menu!\n");
                    return;
                } else if (i > 0 | i < 8) {
                    Command command = commands.get(i);
                    if (command != null) {
                        command.process();
                    }
                } else {
                    view.write("Incorrect input, try again\n");
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }
    }

    private String cutSymbols(String s) {
        return s.replaceAll(",", "\n").replace("[", " ").substring(0,s.length()-1);
    }
}
