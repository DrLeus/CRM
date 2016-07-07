package com.ua.smarterama.andrey.leus.CRM.integration;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;
import com.ua.smarterama.andrey.leus.CRM.controller.command.Help;
import com.ua.smarterama.andrey.leus.CRM.model.Configuration;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import org.junit.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private static Configuration config = new Configuration();

    private final static String DATABASE_NAME = config.getDatabaseName();
    private final static String DB_NAME_TEMP = config.getDatabaseNameTemp();
    private final static String DB_NAME_TEMP_NEW = config.getDatabaseNameTempNew();
    private final static String DB_NAME_TEMP_CRM = config.getDatabaseNameCRM();
    private final static String DB_USER = config.getUserName();
    private final static String DB_PASSWORD = config.getUserPassword();
    private final static String TABLE_NAME = "test";

    private final static String NOT_EXIST_TABLE = "notExistTable";
    private static List<Object> listColumn = new ArrayList<>();
    private static List<Object> list = new ArrayList<>();
    private static List<Object> newData = new ArrayList<>();

    private ConfigurableInputStream in;
    private LogOutputStream out;

    private static DataBaseManager manager;

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
            "Connection succeeded to '" + DB_NAME_TEMP + "'" +
            "\n\n" +
            "Please input command (or 'help'): \n\n";

    @BeforeClass
    public static void init() throws SQLException, ClassNotFoundException {
        manager = new JDBCDataBaseManager();
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
        manager.dropDatabase(DB_NAME_TEMP);
        manager.dropDatabase(DB_NAME_TEMP_NEW);
        manager.dropDatabase(DB_NAME_TEMP_CRM);
        manager.createDatabase(DB_NAME_TEMP);
    }

    @Before
    public void setup() throws SQLException {
        manager.connect(DB_NAME_TEMP, DB_USER, DB_PASSWORD);
        out = new LogOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @After
    public void clearAfterTest() throws SQLException {
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
    }

    @AfterClass
    public static void clearAfterAllTests() throws SQLException {
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
        manager.dropDatabase(DB_NAME_TEMP);
        manager.dropDatabase(DB_NAME_TEMP_NEW);
        manager.dropDatabase(DB_NAME_TEMP_CRM);
    }

    @Test
    public void testConnectToTestDatabase() throws Exception {

        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "See you again!\n\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testHelpDBTest() throws Exception {

        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
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

    }

    @Test
    public void testExitDBTest() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testUnsupportedAfterConnectDBTest() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
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
    }

    @Test
    public void testConnectWithErrorPass() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
        in.add(DB_USER);
        in.add("error");
        in.add("error");
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add(DB_NAME_TEMP);
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
                "Oops...Cant get connection for DB: " + DB_NAME_TEMP +
                "; USER: " + DB_USER + "; PASS: error\n\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Oops...Cant get connection for DB: error" +
                "; USER: " + DB_USER + "; PASS: " + DB_PASSWORD + "\n\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "Oops...Cant get connection for DB: " + DB_NAME_TEMP +
                "; USER: error; PASS: " + DB_PASSWORD + "\n\n" +
                "Please input the database name\n" +
                "Return to main menu!\n\n" +
                "Please input command (or 'help'): \n" +
                "\n" +
                "See you again!\n\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testListAfterConnect() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
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
                DB_NAME_TEMP + "\n\n\n" +
                // exit
                "Please input command (or 'help'): \n" +
                "\r\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testCreateDatabase() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DB_NAME_TEMP_NEW);
        in.add("exit");

        // when
        manager.dropDatabase(DB_NAME_TEMP_NEW);
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "Please input database name for creating:\n\n" +
                "Database " + DB_NAME_TEMP_NEW + " was created\n" +
                // exit
                "\r\n" +
                "Please input command (or 'help'): \n\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testDropDatabase() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DB_NAME_TEMP_NEW);
        in.add("drop");
        in.add(DB_NAME_TEMP_NEW);
        in.add("y");
        in.add("exit");

        // when
        manager.dropDatabase(DB_NAME_TEMP_NEW);
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "Please input database name for creating:\n\n" +
                "Database " + DB_NAME_TEMP_NEW + " was created\n" +
                // exit
                "\r\n" +
                "Please input command (or 'help'): \n\n" +
                "The next data bases available:\n" +
                "\r\n" +
                "1: postgres\n" +
                "2: " + DB_NAME_TEMP + "\n" +
                "3: " + DB_NAME_TEMP_NEW + "\n" +
                "Please select database for dropping:\n" +
                "\n" +
                "Please confirm, do you really want to drop '" + DB_NAME_TEMP_NEW + "' database? Y/N\n\n" +
                "Database '" + DB_NAME_TEMP_NEW + "' dropped\n\n" +
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
        in.add(DB_NAME_TEMP);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DB_NAME_TEMP_NEW);
        in.add("connect");
        in.add(DB_NAME_TEMP_NEW);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
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
        in.add("y");
        in.add("89");
        in.add("8");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = greetingTest +
                "Please input database name for creating:\n" +
                "\n" +
                "Database " + DB_NAME_TEMP_NEW + " was created\n" +
                "\n" +
                "Please input command (or 'help'): \n" +
                "\n" +
                "Please input the database name\n" +
                "Please input user name\n" +
                "Please input password\n" +
                "\n" +
                "\n" +
                "Connection succeeded to '" + DB_NAME_TEMP_NEW + "'\n" +
                "\n" +
                "Please input command (or 'help'): \n\n" +
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
                "Table 'test' was removed! Success!\n" +
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
                "Please input command (or 'help'): \n\n" +
                "See you again!\n\r\n";
        assertEquals(expected.replaceAll("\r\n", "\n"), out.getData().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testCreateInsertUpdateDeleteDBTest() throws Exception {
        // given
        in.add("n");
        in.add("connect");
        in.add(DB_NAME_TEMP);
        in.add(DB_USER);
        in.add(DB_PASSWORD);
        in.add("create");
        in.add(DB_NAME_TEMP_NEW);
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
                "Database " + DB_NAME_TEMP_NEW + " was created\n" +
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
}
