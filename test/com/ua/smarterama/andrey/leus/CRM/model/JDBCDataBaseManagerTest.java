package com.ua.smarterama.andrey.leus.CRM.model;

import org.junit.*;
import org.postgresql.util.PSQLException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

public class JDBCDataBaseManagerTest {

    private static Configuration config = new Configuration();

    private final static String DATABASE_NAME = config.getDatabaseName();
    private final static String DATABASE_NAME_TEMP = config.getDatabaseNameTemp();
    private final static String DB_USER = config.getUserName();
    private final static String DB_PASSWORD = config.getUserPassword();
    private final static String TABLE_NAME = "test";

//    private final static String DATABASE_NAME = "postgrestestnew";
    private final static String NOT_EXIST_TABLE = "notExistTable";
    private static List<Object> listColumn = new ArrayList<>();
    private static List<Object> list = new ArrayList<>();
    private static List<Object> newData = new ArrayList<>();

    private static DataBaseManager manager;

    @BeforeClass
    public static void init() throws SQLException {
        manager = new JDBCDataBaseManager();
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
        manager.dropDatabase(DATABASE_NAME_TEMP);
        manager.createDatabase(DATABASE_NAME_TEMP);
        manager.connect(DATABASE_NAME_TEMP, DB_USER, DB_PASSWORD);
    }

    @Before
    public void  setup() throws SQLException {
        listColumn.clear();
        listColumn.add("code TEXT");
        listColumn.add("name TEXT");
        listColumn.add("price TEXT");
        listColumn.add("");

        manager.createTable(TABLE_NAME, listColumn);

        list = manager.getColumnNames(TABLE_NAME,"");

        newData.clear();
        newData.add("H77435");
        newData.add("SV Flap");
        newData.add("12,00");
    }

    @After
    public void clear() throws SQLException {
        manager.dropTable(TABLE_NAME);
    }


    @AfterClass
    public static void clearAfterAllTests() throws SQLException {
//        manager.connect("", DB_USER, DB_PASSWORD);
//        manager.dropDatabase(DATABASE_NAME_TEMP);
    }

    @Test 
    public void testClear() throws SQLException {

        //given
        List<Object> expected = new ArrayList<>();

        manager.insert(TABLE_NAME, list, newData);

        //when
        manager.clear(TABLE_NAME);
        List<Object> tests = manager.getTableData(TABLE_NAME, "");

        //then
        assertEquals(expected, tests);
    }

    @Test(expected = PSQLException.class) 
    public void testClearNotExistTable() throws SQLException {
        //when
        manager.clear(NOT_EXIST_TABLE);
    }

    @Test(expected = PSQLException.class) 
    public void testConnectToNotExistDatabase() throws SQLException {
        //when
        try {
            manager.connect(NOT_EXIST_TABLE, null, null);
            fail();
        } catch (Exception e) {
            //then
            manager.connect(DATABASE_NAME, null, null);
            throw e;
        }
    }

    @Test(expected = PSQLException.class) 
    public void testConnectToDatabaseWhenIncorrectUserAndPassword() throws SQLException {
        //when
        try {
            manager.connect(DATABASE_NAME, "notExistUser", "qwertyuiop");
            fail();
        } catch (Exception e) {
            //then
            manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
            throw e;
        }
    }

    @Test(expected = PSQLException.class) 
    public void testConnectToServerWhenIncorrectUserAndPassword() throws SQLException {
        //when
        try {
            manager.connect("", "notExistUser", "qwertyuiop");
            fail();
        } catch (Exception e) {
            //then
            manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
            throw e;
        }
    }

    @Test 
    public void testCreateDatabase() throws SQLException {
        //given
        String newDatabase = "createdatabasetest";

        //when
        manager.createDatabase(newDatabase);

        //then
        List<String> databases = manager.getDatabases();
        if (!databases.contains(newDatabase)) {
            fail();
        }
        manager.dropDatabase(newDatabase);
    }

    @Test 
    public void testCreateTable() throws SQLException {
        //given
        List<String> expected = new ArrayList<>(Collections.singletonList(TABLE_NAME));
        manager.dropTable(TABLE_NAME);

        //when
        manager.createTable(TABLE_NAME, listColumn);

        //then
        List<String> actual = manager.getTableNames();
        assertEquals(expected, actual);
    }

    @Test(expected = PSQLException.class) 
    public void testCreateTableWrongQuery() throws SQLException {
        //given
        String query = "testTable(qwerty)";

        //when
        List <Object> list = new ArrayList<>();
        manager.createTable(query, list);
    }

    @Test 
    public void testDropDatabase() throws SQLException {
        //given
        String newDatabase = "dropdatabasetest";
        manager.createDatabase(newDatabase);

        //when
        manager.dropDatabase(newDatabase);

        //then
        List<String> databases = manager.getDatabases();
        if (databases.contains(newDatabase)) {
            fail();
        }
    }

    @Test
    public void testDropTable() throws SQLException {
        //given
        String tableName = "secondTest";
        List<String> expected = new ArrayList<>(Collections.singletonList(TABLE_NAME));

        manager.createTable(tableName, listColumn);

        //when
        manager.dropTable(tableName);

        //then
        List<String> actual = manager.getTableNames();
        assertEquals(expected, actual);
    }

    @Test 
    public void testGetDatabases() {
        //given
        //when
        List<String> actual = manager.getDatabases();

        //then
        assertNotNull(actual);
    }

    @Test 
    public void testGetTableColumns() throws SQLException {
        //given
        List<String> expected = new ArrayList<>(Arrays.asList("id","code", "name", "price"));

        //when
        List<Object> actual = manager.getColumnNames(TABLE_NAME, "");

        //then
        assertEquals(expected, actual);
    }

    @Test 
    public void testGetTableNames() {
        //given
        List<String> expected = new ArrayList<>(Collections.singletonList(TABLE_NAME));

        //when
        List<String> actual = manager.getTableNames();

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = PSQLException.class) 
    public void testInsertNotExistTable() throws SQLException {
        //given
        //when
        //then
        manager.insert(NOT_EXIST_TABLE, newData, listColumn);
    }

    @Test
    public void testDeleteData() throws SQLException { 
        //given
        List<Object> newData = new ArrayList<>();
        newData.add("username");
        newData.add("password");
        newData.add("id");
        newData.clear();

        //when
        manager.delete(1, TABLE_NAME);
//        manager.insert(TABLE_NAME, newData, list);

        //then
        List<Object> user = manager.getTableData(TABLE_NAME, "");
        assertEquals(newData, user);
    }

    @Test 
    public void testUpdate() throws SQLException {
        //given
        List<Object> expected = new ArrayList<>();
        expected.add(new BigDecimal(1));
        expected.add("H77445");
        expected.add("SV Flap 25");
        expected.add("24,00");

        List<Object> list = manager.getColumnNames(TABLE_NAME,"");
        manager.insert(TABLE_NAME, list, newData);

        //when

        List<Object> updateData = new ArrayList<>();
        updateData.add("H77445");
        updateData.add("SV Flap 25");
        updateData.add("24,00");

        manager.update(TABLE_NAME, list, 1, updateData);

        //then
        List<Object> user = manager.getTableData(TABLE_NAME, "");
        assertEquals(expected, user);
    }
}
