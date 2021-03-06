package ua.com.smart.andrey.leus.CRM.model;

import org.junit.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class JDBCDataBaseManagerTest {

    private static Configuration config = new Configuration();

    private final static String DATABASE_NAME = config.getDatabaseName();
    private final static String DATABASE_NAME_TEMP = config.getDatabaseNameTemp();
    private final static String DB_USER = config.getUserName();
    private final static String DB_PASSWORD = config.getUserPassword();
    private final static String TABLE_NAME = "test";
    private final static String NOT_EXIST_TABLE = "notExistTable";

    private static Map<String, String> listColumn = new LinkedHashMap<>();
    private static List<String> list = new ArrayList<>();
    private static List<Object> newData = new ArrayList<>();

    private static DataBaseManager manager;

    @BeforeClass
    public static void init() throws CRMException {
        manager = new JDBCDataBaseManager();
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
        manager.dropDatabase(DATABASE_NAME_TEMP);
        manager.createDatabase(DATABASE_NAME_TEMP);
        manager.connect(DATABASE_NAME_TEMP, DB_USER, DB_PASSWORD);
    }

    @Before
    public void  setup() throws CRMException {

        manager.connect(DATABASE_NAME_TEMP, DB_USER, DB_PASSWORD);

        listColumn.clear();
        listColumn.put("code", "TEXT");
        listColumn.put("name", "TEXT");
        listColumn.put("price", "INTEGER");

        manager.createTable(TABLE_NAME, listColumn);

        list = manager.getColumnNames(TABLE_NAME);

        newData.clear();
        newData.add("H77435");
        newData.add("SV Flap");
        newData.add("12");
    }

    @After
    public void clear() throws CRMException {
        manager.connect(DATABASE_NAME_TEMP, DB_USER, DB_PASSWORD);
        manager.dropTable(TABLE_NAME);
    }

    @AfterClass
    public static void clearAfterAllTests() throws CRMException {
        manager.connect(DATABASE_NAME, DB_USER, DB_PASSWORD);
        manager.dropDatabase(DATABASE_NAME_TEMP);
    }

    @Test 
    public void testClear() throws CRMException {

        //given
        List<Object> expected = new ArrayList<>();

        manager.insert(TABLE_NAME, list, newData);

        //when
        manager.clear(TABLE_NAME);
        List<Object> tests = manager.getTableData(TABLE_NAME);

        //then
        assertEquals(expected, tests);
    }

    @Test(expected = CRMException.class)
    public void testClearNotExistTable() throws CRMException {
        //when
        manager.clear(NOT_EXIST_TABLE);
    }

    @Test(expected = CRMException.class)
    public void testConnectToNotExistDatabase() throws CRMException {
        //when
        try {
            manager.connect(NOT_EXIST_TABLE, null, null);
            fail();
        } catch (Exception e) {
            //then
            throw e;
        }
    }

    @Test(expected = CRMException.class)
    public void testConnectToDatabaseWhenIncorrectUserAndPassword() throws CRMException {
        //when
        try {
            manager.connect(DATABASE_NAME, "notExistUser", "qwertyuiop");
            fail();
        } catch (Exception e) {
            //then
            throw e;
        }
    }

    @Test(expected = CRMException.class)
    public void testConnectToServerWhenIncorrectUserAndPassword() throws CRMException {
        //when
        try {
            manager.connect("", "notExistUser", "qwertyuiop");
            fail();
        } catch (Exception e) {
            //then
            throw e;
        }
    }

    @Test 
    public void testCreateDatabase() throws CRMException {
        //given
        String newDatabase = "createdatabasetestfordropping";

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
    public void testCreateTable() throws CRMException {
        //given
        List<String> expected = new ArrayList<>(Collections.singletonList(TABLE_NAME));
        manager.dropTable(TABLE_NAME);

        //when
        manager.createTable(TABLE_NAME, listColumn);

        //then
        List<String> actual = manager.getTableNames();
        assertEquals(expected, actual);
    }

    @Test(expected = CRMException.class)
    public void testCreateTableWrongQuery() throws CRMException {
        //given
        String query = "testTable(qwerty)";

        //when
        Map <String, String> list = new LinkedHashMap<>();
        manager.createTable(query, list);
    }

    @Test 
    public void testDropDatabase() throws CRMException {
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
    public void testDropTable() throws CRMException {
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
    public void testGetDatabases() throws CRMException {
        //given
        //when
        List<String> actual = manager.getDatabases();

        //then
        assertNotNull(actual);
    }

    @Test 
    public void testGetTableColumns() throws CRMException {
        //given
        List<String> expected = new ArrayList<>(Arrays.asList("id","code", "name", "price"));

        //when
        List<String> actual = manager.getColumnNames(TABLE_NAME);

        //then
        assertEquals(expected, actual);
    }

    @Test 
    public void testGetTableNames() throws CRMException {
        //given
        List<String> expected = new ArrayList<>(Collections.singletonList(TABLE_NAME));

        //when
        List<String> actual = manager.getTableNames();

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = CRMException.class)
    public void testInsertNotExistTable() throws CRMException {
        //given
        //when
        //then
        List list = new ArrayList();

        for (Map.Entry<String, String> pair : listColumn.entrySet()) {
            list.add(pair.getKey());
        }

        manager.insert(NOT_EXIST_TABLE, list, newData);
    }

    @Test
    public void testDeleteData() throws CRMException {
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
        List<Object> user = manager.getTableData(TABLE_NAME);
        assertEquals(newData, user);
    }

    @Test 
    public void testUpdate() throws CRMException {
        //given
        List<Object> expected = new ArrayList<>();
        expected.add(new BigDecimal(1));
        expected.add("H77445");
        expected.add("SV Flap 25");
        expected.add("24");

        List<String> list = manager.getColumnNames(TABLE_NAME);
        manager.insert(TABLE_NAME, list, newData);

        //when

        List<Object> updateData = new ArrayList<>();
        updateData.add("H77445");
        updateData.add("SV Flap 25");
        updateData.add("24");

        manager.update(TABLE_NAME, list, 1, updateData);

        //then
        List<Object> user = manager.getTableData(TABLE_NAME);
        assertEquals(expected, user);
    }
}
