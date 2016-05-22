package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.View;

public class Help extends Command {



    static String text = "\nДанный модуль позволяет реализовать следующие операции:\n" +
            "- получить список доступных баз данных: команда “list”;\n" + //done
            "- подключиться к базе данных: команда “connect”;\n" + //done
            "- создать базу данных: команда “create”;\n" + //done
            "- удалить базу данных: команда “drop”;\n" + //done
            "- посмотреть содержимое таблиц (к примеру, справочник товаров): команда “catalog”;\n" + //done
            "   -- в данном разделе доступно: \n" +
            "      --- добавить, изменить, удалить позицию (например, товар);\n" + // done/done/done
            "      --- добавить, удалить, clear таблицу;" +  //done
            "- получить отчет об остатках на складах: команда “report”;\n" +
            "- оприходование товара на склад: команда “store”;\n" +
            "- списание товар со склада: команда “writeoff”;\n\n" +
            "Для вызова справки введите “help”.\n" + //done
            "Команда “exit” позволяет выйти из модуля (or returrn to main menu)."; //done
    // unsupported //done
    // isconnected // done


    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process() {
        view.write(text);
    }

     public static void getHelp(){ //TODO delete this method, dublicate
         System.out.println(text);;
 }

}
