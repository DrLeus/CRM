package com.ua.smarterama.andrey.leus.CRM.integration;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private final static String DATABASE_NAME = "postgrestest";
    private final static String DATABASE_NAME_NEW = "postgrestestnew";
    private final static String DB_USER = "postgres";
    private final static String DB_PASSWORD = "postgres";
    private final static String TABLE_NAME = "test";
    private final static String NOT_EXIST_TABLE = "notExistTable";
    private static List<Object> listColumn = new ArrayList<>();
    private static List<Object> list = new ArrayList<>();

    private static List<Object> newData = new ArrayList<>();
    private ConfigurableInputStream in;
    private LogOutputStream out;

    private static DataBaseManager manager;

    @BeforeClass
    public static void init() throws SQLException {
        manager = new JDBCDatabaseManager();
        manager.connect("", DB_USER, DB_PASSWORD);
        manager.dropDatabase(DATABASE_NAME);
        manager.dropDatabase(DATABASE_NAME_NEW);
        manager.createDatabase(DATABASE_NAME);
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
    }

    @Before
    public void setup() {

        manager = new JDBCDatabaseManager();
        out = new LogOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

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
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Connection succeeded to "+DATABASE_NAME+"\n" +
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
    public void testConnectToCurrentDatabase() throws Exception {
        // given
        in.add("connect");
        in.add("y");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Connection succeeded to "+DATABASE_NAME+"\n" +
                "\r\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
    }

    @Test
    public void testConnectToAnotherDatabase() throws Exception {
        // given
        in.add("connect");
        in.add("n");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Please input the database name\r\n" +
                "Please input user name\r\n" +
                "Please input password\r\n" +
                "Connection succeeded to " + DATABASE_NAME +
                "\r\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
    }

    @Test
    public void testConnectWithErrorPass() throws Exception {
        // given
        in.add("connect");
        in.add("n");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
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
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Please input the database name\r\n" +
                "Please input user name\r\n" +
                "Please input password\r\n" +
                "Oops...Cant get connection for DB: "+DATABASE_NAME+
                "; USER: "+DB_USER+"; PASS: error\r\n" +
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
        in.add("error");
        in.add("N");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
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
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Oops... something wrong\n" +
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Oops...Cant get connection for DB: "+DATABASE_NAME+
                "; USER: "+DB_USER+"; PASS: error\r\n" +
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
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Connection succeeded to " + DATABASE_NAME +
                "\r\n" +
                "\r\n" +
                // unsupported
                "Please input command: \n" +
                "\r\n" +
                "The next data bases available:\n" +
                "\r\n" +
                "postgres\r\n" +
                "CRM\r\n" +
                DATABASE_NAME + "\r\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
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
    public void testCreateDatabase() throws Exception {
        // given
        in.add("connect");
        in.add("y");
        in.add("create");
        in.add(DATABASE_NAME_NEW);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Connection succeeded to "+DATABASE_NAME+"\n" +
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "\r\n" +
                "Please input database name for creating:\n" +
                "\n" +
                "\n" +
                "Databse "+DATABASE_NAME_NEW+" was created" +
                // exit
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
    }


    @Test
    public void testDropDatabase() throws Exception {
        // given
        in.add("connect");
        in.add("y");
        in.add("create");
        in.add(DATABASE_NAME_NEW);
        in.add("drop");
        in.add(DATABASE_NAME_NEW);
        in.add("y");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Connection succeeded to "+DATABASE_NAME+"\n" +
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "\r\n" +
                "Please input database name for creating:\n" +
                "\n" +
                "\n" +
                "Databse "+DATABASE_NAME_NEW+" was created" +
                // exit
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "\r\n" +
                "The next data bases avaiilable:\n" +
                "\r\n" +
                "1: postgres\n" +
                "2: CRM\n" +
                "3: postgrestest\n" +
                "4: postgrestestnew\n" +
                "\n" +
                "Please select database for dropping:\n" +
                "\n" +
                "Please confirm, do you really want to drop 'postgrestestnew' database? Y/N\n" +
                "Database 'postgrestestnew' dropped\n" +
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected, out.getData());
    }


}
