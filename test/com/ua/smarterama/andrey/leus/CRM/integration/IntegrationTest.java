package com.ua.smarterama.andrey.leus.CRM.integration;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.io.*;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private ConfigurableInputStream in;
    private LogOutputStream out;
    private DataBaseManager databaseManager;

    @Before
    public void setup() {
        databaseManager = new JDBCDataBaseManager();
        out = new LogOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp() throws Exception {
        // given
        in.add("help");
        in.add("exit");

        // when
            Main.main(new String[0]);

        // then
        assertEquals("This programme allows next commands:\n" +
        "- 'connect' - connect to database\n" +
                "- 'list' - get list of databases;\n" +
                "- 'create' - create new database;\n" +
                "- 'drop' - delete the database;\n" +
                "- 'catalog' - get contain of tables (for example information about 'goods';\n" +
                "   -- in this partition you can: \n" +
                "      --- add, change or delete line;\n" +
                "      --- add, delete, clear table;\n" +
                "- 'report' - get goods balance on warehouse ;\n" +
                "- 'store' - add goods on warehouse;\n" +
                "- 'writeoff' - write off goods from warehouse ;\n" +
                "\n" +
                "- 'help' - get list of commands.\n" +
                "- 'exit' - escape from programme or return to main menu.\n" +
                "\n" +
                "Please input command: \n" +
                "\n" +
                "\nThis programme allows next commands:\n" +
                "- 'connect' - connect to database\n" +
                "- 'list' - get list of databases;\n" +
                "- 'create' - create new database;\n" +
                "- 'drop' - delete the database;\n" +
                "- 'catalog' - get contain of tables (for example information about 'goods';\n" +
                "   -- in this partition you can: \n" +
                "      --- add, change or delete line;\n" +
                "      --- add, delete, clear table;\n" +
                "- 'report' - get goods balance on warehouse ;\n" +
                "- 'store' - add goods on warehouse;\n" +
                "- 'writeoff' - write off goods from warehouse ;\n" +
                "\n" +
                "- 'help' - get list of commands.\n" +
                "- 'exit' - escape from programme or return to main menu.\n" +
                "\n" +
                "Please input command: \n" +
                "\n" +
                "\nSee you again!", out.getData());

    }

//    public String getData() {
//        try {
//            String result = new String(out.toByteArray(), "UTF-8");
//            out.reset();
//            return result;
//        } catch (UnsupportedEncodingException e) {
//            return e.getMessage();
//        }
//    }

    @Test
    public void testExit() throws Exception {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testListWithoutConnect() throws Exception {
        // given
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // list
                "Вы не можете пользоваться командой 'list' пока не подключитесь с помощью комманды connect|databaseName|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testFindWithoutConnect() throws Exception {
        // given
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // find|user
                "Вы не можете пользоваться командой 'find|user' пока не подключитесь с помощью комманды connect|databaseName|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testUnsupported() throws Exception {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // unsupported
                "Вы не можете пользоваться командой 'unsupported' пока не подключитесь с помощью комманды connect|databaseName|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n",out.getData());
    }

    @Test
    public void testUnsupportedAfterConnect() throws Exception {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // unsupported
                "Несуществующая команда: unsupported\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testListAfterConnect() throws Exception {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // list
                "[user, test]\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testFindAfterConnect() throws Exception {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // find|user
                "--------------------\n" +
                "|name|password|id|\n" +
                "--------------------\n" +
                "--------------------\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testConnectAfterConnect() throws Exception {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("list");
        in.add("connect|test|postgres|postgres");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect sqlcmd
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // list
                "[user, test]\n" +
                "Введи команду (или help для помощи):\n" +
                // connect test
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // list
                "[qwe]\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testConnectWithError() throws Exception {
        // given
        in.add("connect|sqlcmd");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect sqlcmd
                "Неудача! по причине: Неверно количество параметров разделенных знаком '|', ожидается 4, но есть: 2\n" +
                "Повтори попытку.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testFindAfterConnect_withData() throws Exception {
        // given
//        databaseManager.connect("sqlcmd", "postgres", "postgres");
//
//        databaseManager.clear("user");
//
//        DataSet user1 = new DataSet();
//        user1.put("id", 13);
//        user1.put("name", "Stiven");
//        user1.put("password", "*****");
//        databaseManager.create("user", user1);
//
//        DataSet user2 = new DataSet();
//        user2.put("id", 14);
//        user2.put("name", "Eva");
//        user2.put("password", "+++++");
//        databaseManager.create("user", user2);

        in.add("connect|sqlcmd|postgres|postgres");
        in.add("clear|user");
        in.add("create|user|id|13|name|Stiven|password|*****");
        in.add("create|user|id|14|name|Eva|password|+++++");
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // clear|user
                "Таблица user была успешно очищена.\n" +
                "Введи команду (или help для помощи):\n" +
                // create|user|id|13|name|Stiven|password|*****
                "Запись {names:[id, name, password], values:[13, Stiven, *****]} была успешно создана в таблице 'user'.\n" +
                "Введи команду (или help для помощи):\n" +
                // create|user|id|14|name|Eva|password|+++++
                "Запись {names:[id, name, password], values:[14, Eva, +++++]} была успешно создана в таблице 'user'.\n" +
                "Введи команду (или help для помощи):\n" +
                // find|user
                "--------------------\n" +
                "|name|password|id|\n" +
                "--------------------\n" +
                "|Stiven|*****|13|\n" +
                "|Eva|+++++|14|\n" +
                "--------------------\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testClearWithError() throws Exception {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("clear|sadfasd|fsf|fdsf");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // clear|sadfasd|fsf|fdsf
                "Неудача! по причине: Формат команды 'clear|tableName', а ты ввел: clear|sadfasd|fsf|fdsf\n" +
                "Повтори попытку.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }

    @Test
    public void testCreateWithErrors() throws Exception {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("create|user|error");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // create|user|error
                "Неудача! по причине: Должно быть четное количество параметров в формате 'create|tableName|column1|value1|column2|value2|...|columnN|valueN', а ты прислал: 'create|user|error'\n" +
                "Повтори попытку.\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", out.getData());
    }
}
