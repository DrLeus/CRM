package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

public class Help extends Command {


    public Help(Console view) {
        super(view);
    }

    static String text = "\nThis programme allows next commands:\n" +
            "- “connect” - connect to database\n" +
            "- “list“ - get list of databases”;\n" +
            "- “create” - create new database;\n" +
            "- “drop” - delete the database;\n" +
            "- “catalog” - get contain of tables (for example information about 'goods';\n" +
            "   -- in this partition you can: \n" +
            "      --- add, change or delete line;\n" +
            "      --- add, delete, clear table;\n" +
            "- “report” - get goods balance on warehouse ;\n" +
            "- “store” - add goods on warehouse;\n" +
            "- “writeoff” - write off goods from warehouse ;\n" +
            "\n" +
            "- “help” - get list of commands.\n" +
            "- “exit” - escape from programme or return to main menu.";

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process() {
        view.write(text);
    }

    public static void getHelp() { //TODO delete this method, dublicate
        System.out.println(text);
    }

}
