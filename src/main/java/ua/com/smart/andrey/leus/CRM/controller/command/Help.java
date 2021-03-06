package ua.com.smart.andrey.leus.CRM.controller.command;


import ua.com.smart.andrey.leus.CRM.view.Console;
import ua.com.smart.andrey.leus.CRM.view.View;

public class Help extends Command {

    public Help(View view) {
        super(view);
    }

    static String text = "This programme provides next commands:\n" +
            "- 'connect' - connect to database\n" +
            "- 'list' - get list of databases;\n" +
            "- 'create' - create new database;\n" +
            "- 'drop' - delete the database;\n" +
            "- 'catalog' - get contain of tables (for example information about 'goods';\n" +
            "   -- in this partition you can: \n" +
            "      --- add, update or delete rows and columns;\n" +
            "      --- add, delete, clear table;\n" +
            "\n" +
            "- 'help' - get list of commands.\n" +
            "- 'exit' - escape from programme or return to main menu.\n";

    static String connect =
            "\n" +
                    "Please connect to database.\n";

    @Override
    public boolean canProcess(String command) {
        return "help".equals(command);
    }

    @Override
    public void process() {
        view.write(text);
    }

    public static String getHelp() { //TODO delete this method, dublicate
       return text;
    }

    public static void getConnect() { //TODO delete this method, dublicate
        System.out.println(connect);
    }

}
