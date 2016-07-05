package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Admin on 04.07.2016.
 */
public class SelectedDBTest {

    private Console view;
    private DataBaseManager manager;
    private Command command;

    @Before
    public void setup() {
        view = mock(Console.class);
        manager = mock(JDBCDataBaseManager.class);
        command = new SelectDataBase(manager, view);
    }


    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("list");
        assertTrue(canProcess);
    }

    @Test
    public void testCanNotProcess() throws Exception {
        boolean canProcess = command.canProcess("error");
        assertFalse(canProcess);
    }

    @Test
    public void testProcess() throws SQLException {

        //given
        List<String> list = new ArrayList<>();
        list.add("test");
        list.add("temp");

        //when
        when(manager.getDatabases()).thenReturn(list);
        command.process();

        //then
        verify(view).write("The next data bases available:\n");
        verify(manager).getDatabases();
    }

}
