package com.ua.smarterama.andrey.leus.CRM.integration;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;
import com.ua.smarterama.andrey.leus.CRM.controller.command.Help;
import com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse.InitialDB_CRM;
import com.ua.smarterama.andrey.leus.CRM.model.Configuration;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import org.junit.*;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class IntegrationTest_crm {

    private static Configuration config = new Configuration();

    private final static String DATABASE_NAME = config.getDatabaseName();
    private final static String DB_NAME_TEMP_CRM = config.getDatabaseNameCRM();
    private final static String DB_USER = config.getUserName();
    private final static String DB_PASSWORD = config.getUserPassword();

    private ConfigurableInputStream in;
    private LogOutputStream out;

    private static DataBaseManager manager;

    String greetingCRM = "Do you want to initialize and than connect to database CRM for showing all abilities of module? (Y) or connect to your database (N)?;\n" +
            "\n" +
            "Please wait!\n" +
            "\n" +
            "Connection succeeded to crmasanexample\n\n" + Help.getHelp() +
            "\n" +
            "Additional commands\n" +
            " - 'report' - get goods balance on warehouse ;\n" +
            " - 'store' - add goods on warehouse +\n" +
            " - 'writeoff' - write off goods from warehouse ;\n" +
            "\n" +
            "Please input command (or 'help'): " +
            "\n\n";

    @BeforeClass
    public static void init() throws SQLException, ClassNotFoundException {
        manager = new JDBCDataBaseManager();
    }

    @Before
    public void setup() throws SQLException {
        out = new LogOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @After
    public void clearAfterTest() throws SQLException {
    }

    @AfterClass
    public static void clearAfterAllTests() throws SQLException {
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
        manager.dropDatabase(DB_NAME_TEMP_CRM);
    }

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


}
