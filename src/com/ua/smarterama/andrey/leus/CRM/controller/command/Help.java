package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.View;

public class Help implements Command {

    private View view;

    static String text = "\nДанный модуль позволяет реализовать следующие операции:\n" +
            "- получить список доступных баз данных: команда “listDB”;\n" +
            "- подключиться к базе данных: команда “connectDB”;\n" +
            "- удалить базу данных: команда “dropDB”;\n" +
            "- получить отчет об остатках на складах: команда “report”;\n" +
            "- посмотреть справочник товаров: команда “catalog”;\n" +
            "   (в разделе справочник доступно: добавить, изменить, удалить товар.)\n" +
            "- оприходование товара на склад: команда “store”;\n" +
            "- списание товар со склада: команда “writeoff”;\n" +
            "Для вызова справки введите “help”.\n" +
            "Команда “exit” позволяет выйти из модуля.\n";

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
            view.write(text);
    }

     public static void getHelp(){ //TODO delete this method, dublicate
         System.out.println(text);;
 }

}
