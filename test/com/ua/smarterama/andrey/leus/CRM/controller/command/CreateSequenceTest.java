package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDatabaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Admin on 29.05.2016.
 */
public class CreateSequenceTest {

    private Command command;
    private JDBCDatabaseManager manager = new JDBCDatabaseManager();
    private Console view;

    @Before
    public void setup() {
        manager = mock(JDBCDatabaseManager.class);
        view = mock(Console.class);
        command = new ConnectToDataBase(manager, view);
    }

    @Test
    public void testCanProcessWithParameters() {
        //when
//        UserInput input = new UserInput("connect|test|user|password");
//        boolean is = command.is(input);

        //then
//        assertTrue(is);
    }

    @Test
    public void testCanProcessWithoutParameters() {
        //when
//        UserInput input = new UserInput("connect");
//        boolean is = command.is(input);
//
//        //then
//        assertTrue(is);
    }

    @Test
    public void testProcess() throws SQLException {
        //when

        //then
        verify(command).manager.connect("CRM", "postgres", "postgres");
        verify(view).write("Connection succeeded to CRM");

    }
}
