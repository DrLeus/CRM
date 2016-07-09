package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testProcess() throws CRMException {

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
