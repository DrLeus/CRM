package com.ua.smarterama.andrey.leus.CRM.integration;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;
import com.ua.smarterama.andrey.leus.CRM.controller.command.Help;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.AfterClass;
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

    String greetingCRM = "Do you want to initialize and than connect to database CRM for showing all abilities of module? (Y) or connect to your database (N)?;\n" +
            "\n" +
            "Please wait!\n" +
            "\n" +
            "Connection succeeded to CRM\n\n" + Help.getHelp() +
            "\n" +
            "Additional commands\n" +
            " - 'report' - get goods balance on warehouse ;\n" +
            " - 'store' - add goods on warehouse +\n" +
            " - 'writeoff' - write off goods from warehouse ;\n" +
            "\n" +
            "Please input command (or 'help'): " +
            "\n\n";

    String greetingTest = "Do you want to initialize and than connect to database CRM for showing all abilities of module? (Y) or connect to your database (N)?;" +
            "\n\n" +
            Help.getHelp() +
            "\n\n" +
            "Please connect to database.\n\n" +
            "Please input command (or 'help'): \n" +
            "\n" +
            "Please input the database name\n" +
            "Please input user name\n" +
            "Please input password\n\n\n" +
            "Connection succeeded to '" + DATABASE_NAME + "'" +
            "\n\n" +
            "Please input command (or 'help'): \n\n";

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
    public void setup() throws SQLException {

        manager = new JDBCDataBaseManager();
        out = new LogOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @AfterClass
    public static void clearAfterAllTests() throws SQLException {
        manager.connect("", DB_USER, DB_PASSWORD);
//        manager.dropDatabase(DATABASE_NAME);
        manager.dropDatabase(DATABASE_NAME_NEW);
    }

    @Test
    public void testConnectToTestDatabase() throws Exception {

        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "See you again!\n\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testHelpDBTest() throws Exception {

        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("help");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                Help.getHelp() +
                "\r\n" +
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));

    } //+

    @Test
    public void testExitDBTest() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testUnsupportedAfterConnectDBTest() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                // connect
                "Oops...incorrect command!\n\n" +
                // exit
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testConnectWithErrorPass() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add("error");
        in.add("error");
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add(DATABASE_NAME);
        in.add("error");
        in.add(DB_PASSWORD);
        in.add("exit");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = "Do you want to initialize and than connect to database CRM for showing all abilities of module? (Y) or connect to your database (N)?;" +
                "\n\n" +
                Help.getHelp() +
                "\n\n" +
                "Please connect to database.\n\n" +
                "Please input command (or 'help'): \n" +
                "\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Oops...Cant get connection for DB: " + DATABASE_NAME +
                "; USER: " + DB_USER + "; PASS: error\n\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Oops...Cant get connection for DB: error" +
                "; USER: " + DB_USER + "; PASS: " + DB_PASSWORD + "\n\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Oops...Cant get connection for DB: " + DATABASE_NAME +
                "; USER: error; PASS: " + DB_PASSWORD + "\n\n" +
                "Please input the database name\n" +
                "Return to main menu!\n\n" +
                "Please input command (or 'help'): \n" +
                "\n" +
                "See you again!\n\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testListAfterConnect() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                // connect
                "The next data bases available:\n" +
                "\r\n" +
                "postgres\r\n" +
                "CRM\n" +
                DATABASE_NAME + "\n\n\n" +
                // exit
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testCreateDatabase() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DATABASE_NAME_NEW);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "Please input database name for creating:\n\n" +
                "Database " + DATABASE_NAME_NEW + " was created\n" +
                // exit
                "\r\n" +
                "Please input command (or 'help'): \n\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testDropDatabase() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DATABASE_NAME_NEW);
        in.add("drop");
        in.add(DATABASE_NAME_NEW);
        in.add("y");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "Please input database name for creating:\n\n" +
                "Database " + DATABASE_NAME_NEW + " was created\n" +
                // exit
                "\r\n" +
                "Please input command (or 'help'): \n\n" +
                "The next data bases avaiilable:\n" +
                "\r\n" +
                "1: postgres\n" +
                "2: CRM\n" +
                "3: " + DATABASE_NAME + "\n" +
                "4: " + DATABASE_NAME_NEW + "\n" +
                "Please select database for dropping:\n" +
                "\n" +
                "Please confirm, do you really want to drop 'postgrestestnew' database? Y/N\n\n" +
                "Database 'postgrestestnew' dropped\n\n" +
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testConnectToCRMDatabase() throws Exception {

        // given
        in.add("y");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingCRM +
                "See you again!\n\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    } //+

    @Test
    public void testReportAfterConnectCRM() throws Exception {
        // given
        in.add("y");
        in.add("report");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingCRM +
                "The warehouse contains:\n" +
                "\n" +
                "id  code    name                   quantity  \n" +
                "\n" +
                "3   H77484  SEAL SV-50 EPDM CAT 2  50        \n" +
                "\n" +
                "4   H77509  SEAL SV-65 EPDM CAT 2  20        \n" +
                "\n" +
                // exit
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testStoreANDWriteoffAfterConnect() throws Exception {
        // given
        in.add("y");
        in.add("store");
        in.add("error");
        in.add("3");
        in.add("error");
        in.add("10");
        in.add("writeoff");
        in.add("3");
        in.add("10");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingCRM +
                "Please input id of goods:\n" +
                "\n" +
                "Incorrect input, try again\n" +
                "\n" +
                "Please input id of goods:\n" +
                "\n" +
                "Please input quantity of goods:\n" +
                "\n" +
                "Incorrect input, try again" +
                "\n\n" +
                "Please input quantity of goods:\n" +
                "\n" +
                "The goods was added! Success!\n" +
                "\n" +
                // writeoff
                "Please input command (or 'help'): \n" +
                "\n" +
                "The warehouse contains:\n" +
                "\n" +
                "id  code    name                   quantity  \n" +
                "\n" +
                "3   H77484  SEAL SV-50 EPDM CAT 2  60        \n" +
                "\n" +
                "4   H77509  SEAL SV-65 EPDM CAT 2  20        \n" +
                "\n" +
                "Please input id of goods:\n" +
                "\n" +
                "Please input quantity of goods:\n" +
                "\n" +
                "The goods was wrote off! Success!\n" +
                "\n" +
                // exit
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testCreateInsertClearDropTableDBTest() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DATABASE_NAME_NEW);
        in.add("catalog");
        in.add("5");
        in.add("exit");
        in.add("5");
        in.add(TABLE_NAME);
        in.add("name TEXT");
        in.add("price TEXT");
        in.add("");
        in.add("1");
        in.add("1");
        in.add("2");
        in.add("1");
        in.add("sv");
        in.add("22");
        in.add("1");
        in.add("1");
        in.add("7");
        in.add("1");
        in.add("y");
        in.add("1");
        in.add("1");
        in.add("6");
        in.add("1");
//        in.add("error");
//        in.add("1");
        in.add("y");
        in.add("89");
        in.add("8");
        in.add("drop");
        in.add("4");
        in.add("y");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "Please input database name for creating:\n" +
                "\n" +
                "Database postgrestestnew was created\n" +
                "\n" +
                "Please input command (or 'help'): \n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please input table name:\n" +
                "\n" +
                "Return to main menu!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please input table name:\n" +
                "\n" +
                "Please input name of columns and type (for ex. TEXT; for column 'name' must be 'name TEXT')\n" +
                "The first column = 'id' with auto-increment\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "The table test was created! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "The table is empty!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "Please input data for column 'name'\n" +
                "\n" +
                "Please input data for column 'price'\n" +
                "\n" +
                "The row was created! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "1   sv    22     \n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "Please confirm, do you really want to clear table 'test'? Y/N\n" +
                "\n" +
                "Table 'test' was cleared! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "The table is empty!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "Please confirm, do you really want to remove 'test' table? Y/N\n" +
                "\n" +
                "Table 'test'was removed! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Incorrect input, try again\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Return to main menu!\n\n" +
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "The next data bases avaiilable:\n" +
                "\n" +
                "1: postgres\n" +
                "2: postgrestest\n" +
                "3: CRM\n" +
                "4: postgrestestnew\n" +
                "Please select database for dropping:\n" +
                "\n" +
                "Please confirm, do you really want to drop 'postgrestestnew' database? Y/N\n" +
                "\n" +
                "Database 'postgrestestnew' dropped\n" +
                "\n" +
                "Please input command (or 'help'): \n\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testCreateInsertUpdateDeleteDBTest() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DATABASE_NAME);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DATABASE_NAME_NEW);
        in.add("catalog");
        in.add("5");
        in.add(TABLE_NAME);
        in.add("name TEXT");
        in.add("price TEXT");
        in.add("");
        in.add("2");
        in.add("1");
        in.add("sv");
        in.add("22");
        in.add("2");
        in.add("1");
        in.add("svs");
        in.add("33");
        in.add("1");
        in.add("1");
        in.add("3");
        in.add("1");
        in.add("1");
        in.add("svs");
        in.add("44");
        in.add("1");
        in.add("1");
        in.add("4");
        in.add("1");
        in.add("1");
        in.add("y");
        in.add("1");
        in.add("1");
        in.add("8");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "Please input database name for creating:\n" +
                "\n" +
                "Database postgrestestnew was created\n" +
                "\n" +
                "Please input command (or 'help'): \n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please input table name:\n" +
                "\n" +
                "Please input name of columns and type (for ex. TEXT; for column 'name' must be 'name TEXT')\n" +
                "The first column = 'id' with auto-increment\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "Please input name for next column\n" +
                "\n" +
                "The table test was created! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "Please input data for column 'name'\n" +
                "\n" +
                "Please input data for column 'price'\n" +
                "\n" +
                "The row was created! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Please select table\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "Please input data for column 'name'\n" +
                "\n" +
                "Please input data for column 'price'\n" +
                "\n" +
                "The row was created! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "1   sv    22     \n" +
                "\n" +
                "2   svs   33     \n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "1   sv    22     \n" +
                "\n" +
                "2   svs   33     \n" +
                "\n" +
                "\n" +
                "\n" +
                "Please select row id to update:\n" +
                "\n" +
                "Please input data for column 'name'\n" +
                "\n" +
                "Please input data for column 'price'\n" +
                "\n" +
                "The row was updated! Success!\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "1   svs   44     \n" +
                "\n" +
                "2   svs   33     \n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "1   svs   44     \n" +
                "\n" +
                "2   svs   33     \n" +
                "\n" +
                "Please input 'id' line to delete\n" +
                "\n" +
                "Please confirm, do you really want to remove position id='1'? Y/N\n" +
                "\n" +
                "Id '1' removed\n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Database has next tables:\n" +
                "\n" +
                "1: test\n" +
                "Please select table:\n" +
                "\n" +
                "id  name  price  \n" +
                "\n" +
                "2   svs   33     \n" +
                "\n" +
                "Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n" +
                "\n" +
                "Please select operation:\n" +
                "\n" +
                "Return to main menu!\n" +
                "\n" +
                "Please input command (or 'help'): \n" +
                "\n" +
                "See you again!\n\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

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
        String expected = greetingCRM +
                // connect
                "\r\n" +
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "Do you want to connect to current database (" + DATABASE_NAME + ")? (Y/N)\r\n" +
                "Connection succeeded to postgrestest\n" +
                "\n" +
                "Please input command (or 'help'): \n" +
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
                "\n";
//                +
//                // exit
//                "Please input command (or 'help'): \n" +
//                "\r\n" +
//                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    public void testC() throws Exception {
        // given
        in.add("connect");
        in.add("y");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingCRM +
                // connect
                "\r\n" +
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "Do you want to connect to current database (" + DATABASE_NAME + ")? (Y/N)\r\n" +
                "Connection succeeded to " + DATABASE_NAME + "\n" +
                "\r\n" +
                // exit
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    public void testCo() throws Exception {
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
        String expected = greetingCRM +
                // connect
                "\r\n" +
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "Do you want to connect to current database (" + DATABASE_NAME + ")? (Y/N)\r\n" +
                "Oops... something wrong\n" +
                "Do you want to connect to current database (" + DATABASE_NAME + ")? (Y/N)\r\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Oops...Cant get connection for DB: " + DATABASE_NAME +
                "; USER: " + DB_USER + "; PASS: error\r\n" +
                // exit
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }
}
