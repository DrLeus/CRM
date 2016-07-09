package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateDatabaseTest {

    private Console view;
    private DataBaseManager manager;
    private Command command;

    @Before
    public void setup() {
        view = mock(Console.class);
        manager = mock(JDBCDataBaseManager.class);
        command = new CreateDatabase(manager, view);
    }


    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("create");
        assertTrue(canProcess);
    }

    @Test
    public void testCanNotProcess() throws Exception {
        boolean canProcess = command.canProcess("false");
        assertFalse(canProcess);
    }

    @Test
    public void testProcess() throws CRMException {
        //when
        when(view.read()).thenReturn("temp");
        command.process();

        //then
        verify(view).write("Please input database name for creating:\n");
        verify(manager).createDatabase("temp");
        verify(view).write("Database temp was created\r\n");
    }
}
