package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.View;

public class Help extends Command {



    static String text = "\nДанный модуль позволяет реализовать следующие операции:\n" +
            "- получить список доступных баз данных: команда “list”;\n" +
            "- подключиться к базе данных: команда “connect”;\n" +
            "- создать базу данных: команда “create”;\n" +
            "- удалить базу данных: команда “drop”;\n" +
            "- посмотреть содержимое таблиц (к примеру, справочник товаров): команда “catalog”;\n" +
            "   -- в данном разделе доступно: \n" +
            "      --- добавить, изменить, удалить позицию (например, товар);\n" +
            "      --- добавить, удалить, clear таблицу;" +
            "- получить отчет об остатках на складах: команда “report”;\n" +
            "- оприходование товара на склад: команда “store”;\n" +
            "- списание товар со склада: команда “writeoff”;\n\n" +
            "Для вызова справки введите “help”.\n" +
            "Команда “exit” позволяет выйти из модуля (or returrn to main menu).";

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
