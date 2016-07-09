package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.Configuration;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;


import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConnectTest {

    private ConnectToDataBase command;
    private DataBaseManager manager;
    private Console view;
    private static Configuration config = new Configuration();

    @Before
    public void setup() {
        manager = mock(JDBCDataBaseManager.class);
        view = mock(Console.class);
        command = new ConnectToDataBase(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("connect");
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
        when(view.read()).thenReturn(config.getDatabaseName());
        when(view.read()).thenReturn(config.getUserName());
        when(view.read()).thenReturn(config.getUserPassword());
        command.process();

        //then
        verify(manager).connect(config.getDatabaseName(), config.getUserName(), config.getUserPassword());
        verify(view).write("\n");
        verify(view).write(("Connection succeeded to '"+config.getDatabaseName()+"'\r\n"));

    }
}
