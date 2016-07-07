package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.Configuration;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

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
    public void testProcess() throws SQLException {
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
