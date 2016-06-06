package com.ua.smarterama.andrey.leus.CRM.integration;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
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
        manager = new JDBCDataBaseManager();
        manager.connect("", DB_USER, DB_PASSWORD);
        manager.dropDatabase(DATABASE_NAME);
        manager.dropDatabase(DATABASE_NAME_NEW);
        manager.createDatabase(DATABASE_NAME);
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
    }

    @Before
    public void setup() {

        manager = new JDBCDataBaseManager();
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
            "- 'Report' - get goods balance on warehouse ;\n" +
            "- 'Store' - add goods on warehouse;\n" +
            "- 'writeoff' - write off goods from warehouse ;\n" +
            "\n" +
            "- 'help' - get list of commands.\n" +
            "- 'exit' - escape from programme or return to main menu.\n" +
            "\n" +
            "Please connect to database.\n";;

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
        assertEquals(expected.replaceAll("\r\n", "n"), out.getData().replaceAll("\r\n", "n"));

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
        assertEquals(expected.replaceAll("\r\n", "n"), out.getData().replaceAll("\r\n", "n"));
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
        assertEquals(expected.replaceAll("\r\n", "n"), out.getData().replaceAll("\r\n", "n"));
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
        assertEquals(expected.replaceAll("\r\n", "n"), out.getData().replaceAll("\r\n", "n"));
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
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
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
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
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
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
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
        assertEquals(expected.replaceAll("\r\n", "n"), out.getData().replaceAll("\r\n", "n"));
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
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
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
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testReportAfterConnect() throws Exception {
        // given
        in.add("connect");
        in.add("N");
        in.add("CRM");
        in.add("postgres");
        in.add("postgres");
        in.add("Report");
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
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Connection succeeded to CRM"  +
                "\r\n" +
                // Store
                "Please input command: \n" +
                "\r\n" +
                "\r\n" +
                "The warehouse contains:\n" +
                "\n" +
                "id  code    name                   quantity  \n" +
                "\n" +
                "1   H77435  SEAL SV-25 EPDM CAT 2  55        \n" +
                "\n" +
                "3   H77484  SEAL SV-50 EPDM CAT 2  75        \n" +
                "\n" +
                "4   H77509  SEAL SV-65 EPDM CAT 2  20        \n" +
                "\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }
    @Test
    public void testStoreANDWriteoffAfterConnect() throws Exception {
        // given
        in.add("connect");
        in.add("N");
        in.add("CRM");
        in.add("postgres");
        in.add("postgres");
        in.add("Store");
        in.add("error");
        in.add("1");
        in.add("error");
        in.add("10");
        in.add("writeoff");
        in.add("1");
        in.add("10");
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
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Connection succeeded to CRM"  +
                "\r\n" +
                // Store
                "Please input command: \n" +
                "\r\n" +
                "\r\n" +
                "Please input id of goods:\n" +
                "\n" +
                "Incorrect input, try again\n" +
                "\n" +
                "Please input id of goods:\n" +
                "\n" +
                "\n" +
                "Please input quantity of goods:\n" +
                "\n" +
                "Incorrect input, try again" +
                "\n" +
                "\n" +
                "Please input quantity of goods:\n" +
                "\n" +
                "The goods was added! Success!\n" +
                "\n" +
                // writeoff
                "Please input command: \n" +
                "\n" +
                "\n" +
                "The warehouse contains:\n" +
                "\n" +
                "id  code    name                   quantity  \n" +
                "\n" +
                "1   H77435  SEAL SV-25 EPDM CAT 2  65        \n" +
                "\n" +
                "3   H77484  SEAL SV-50 EPDM CAT 2  75        \n" +
                "\n" +
                "4   H77509  SEAL SV-65 EPDM CAT 2  20        \n" +
                "\n" +
                "\n" +
                "Please input id of goods:\n" +
                "\n" +
                "\n" +
                "Please input quantity of goods:\n" +
                "\n" +
                "The goods was wrote off! Success!\n" +
                "\n" +
                // exit
                "Please input command: \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testCatalogAfterConnect() throws Exception {
        // given
        in.add("connect");
        in.add("y");
        in.add("catalog");
        in.add("9");
        in.add("5");
        in.add("test");
        in.add("code");
        in.add("price");
        in.add("");
        in.add("5");
        in.add("retro");
        in.add("name");
        in.add("qty");
        in.add("");
        in.add("1");
        in.add("3");
        in.add("error");
        in.add("test");
        in.add("2");
        in.add("1");
        in.add("H1111");
        in.add("12,00");
        in.add("3");
        in.add("1");
        in.add("error");
        in.add("1");
        in.add("H2222");
        in.add("24,00");
        in.add("2");
        in.add("1");
        in.add("H333");
        in.add("100,00");
        in.add("4");
        in.add("1");
        in.add("2");
        in.add("n");
        in.add("4");
        in.add("1");
        in.add("error");
        in.add("2");
        in.add("y");
        in.add("6");
        in.add("2");
        in.add("n");
        in.add("6");
        in.add("2");
        in.add("y");
        in.add("7");
        in.add("1");
        in.add("n");
        in.add("7");
        in.add("1");
        in.add("y");
        in.add("6");
        in.add("1");
        in.add("y");
        in.add("8");


        // when
        Main.main(new String[0]);

        // then
        String expected = greeting +
                // connect
                "\r\n" +
                "Please input command: \n" +
                "\r\n" +
                "Do you want to connect to current database ("+DATABASE_NAME+")? (Y/N)\r\n" +
                "Connection succeeded to postgrestest\n" +
                "\n" +
                "Please input command: \n" +
                "\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Incorrect input, try again\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                "Please input table name:\n" +
                "\n" +
                "\n" +
                "Please input name of columns\n" +
                "The first column = 'id' with auto-increment\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "The table test was created! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                "Please input table name:\n" +
                "\n" +
                "\n" +
                "Please input name of columns\n" +
                "The first column = 'id' with auto-increment\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "The table retro was created! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Incorrect input, try again\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Incorrect input, try again\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "id  code  price  \n" +
                "\n" +
                "\n" +
                "The table is empty!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Please input data for column 'code'\n" +
                "\n" +
                "Please input data for column 'price'\n" +
                "\n" +
                "\n" +
                "The row was created! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "id  code   price  \n" +
                "\n" +
                "1   H1111  12,00  \n" +
                "\n" +
                "\n" +
                "\n" +
                "Please select row id to update: \n" +
                "Incorrect input, try again\n" +
                "\n" +
                "\n" +
                "Please select row id to update: \n" +
                "Please input data for column 'code'\n" +
                "\n" +
                "Please input data for column 'price'\n" +
                "\n" +
                "\n" +
                "The row was updated! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Please input data for column 'code'\n" +
                "\n" +
                "Please input data for column 'price'\n" +
                "\n" +
                "\n" +
                "The row was created! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "id  code   price   \n" +
                "\n" +
                "1   H2222  24,00   \n" +
                "\n" +
                "2   H333   100,00  \n" +
                "\n" +
                "Please input 'id' line to delete\n" +
                "\n" +
                "Please confirm, do you really want to remove position id='2'? Y/N\n" +
                "Your action canceled!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "id  code   price   \n" +
                "\n" +
                "1   H2222  24,00   \n" +
                "\n" +
                "2   H333   100,00  \n" +
                "\n" +
                "Please input 'id' line to delete\n" +
                "\n" +
                "Incorrect input, try again\n" +
                "Please input 'id' line to delete\n" +
                "\n" +
                "Please confirm, do you really want to remove position id='2'? Y/N\n" +
                "Id '2' removed\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Please confirm, do you really want to remove 'retro' table? Y/N\n" +
                "Your action canceled!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "2: retro\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Please confirm, do you really want to remove 'retro' table? Y/N\n" +
                "Table 'retro'was removed! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Please confirm, do you really want to clear table 'test'? Y/N\n" +
                "Your action canceled!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Please confirm, do you really want to clear table 'test'? Y/N\n" +
                "Table 'test' was cleared! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "\n" +
                " Database has next tables: \n" +
                "\n" +
                "1: test\n" +
                "\n" +
                "Please select table:\n" +
                "\n" +
                "Please confirm, do you really want to remove 'test' table? Y/N\n" +
                "Table 'test'was removed! Success!\n" +
                "\n" +
                "Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "\n" +
                "Please select operation:\n" +
                "\n"
                ;
//                +
//                // exit
//                "Please input command: \n" +
//                "\r\n" +
//                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }
}
