package com.ua.smarterama.andrey.leus.CRM.integration;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;


import java.io.*;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private ConfigurableInputStream in;
    private LogOutputStream out;
    private DataBaseManager databaseManager;
    private static final String DB_NAME = "CRM";
    private final static String DB_USER = "postgres";
    private final static String DB_PASSWORD = "postgres";

    String greeting = "This programme allows next commands:\n" +
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
            "- 'exit' - escape from programme or return to main menu.\n";

    @Before
    public void setup() {

        databaseManager = new JDBCDatabaseManager();
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
        String expected = greeting +
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                greeting +
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());

    }

    @Test
    public void testExit() throws Exception {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals(greeting +
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n", out.getData());
    }

    @Test
    public void testListWithoutConnect() throws Exception {
        // given
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Oops... Please connect to database!\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
    }

    @Test
    public void testUnsupportedAfterConnect() throws Exception {
        // given
        in.add("connect");
        in.add("Y");
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database (CRM)? (Y/N)\r\n" +
                "Connection succeeded to CRM\n" +
                "\r\n" +
                // unsupported
                "Please input command: \n\r\n" +
                "\n" +
                "Oops...incorrect command!\r\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
    }

    @Test
    public void testListAfterConnect() throws Exception {
        // given
        in.add("connect");
        in.add("Y");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database (CRM)? (Y/N)\r\n" +
                "Connection succeeded to CRM\n" +
                "\r\n" +
                // unsupported
                "Please input command: \n\r\n" +
                "The next data bases available:\n\r" +
                "\n" +
                "postgres\r\n" +
                "CRM\r\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
    }

    @Test
    public void testConnectWithError() throws Exception {
        // given
        in.add("connect");
        in.add("n");
        in.add("CRM");
        in.add("postgres");
        in.add("error");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database (CRM)? (Y/N)\r\n" +
                "Please input the database name\r\n" +
                "Please input user name\r\n" +
                "Please input password\r\n" +
                "Oops...Cant get connection for DB: CRM; USER: postgres; PASS: error\r\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
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
