package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.Console;
import org.junit.Test;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by indigo on 01.09.2015.
 */
public class ExitTest {

    private DataBaseManager manager;
    private Console view;

    @Test
    public void testCanProcessExitString() {
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("exit");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_thowsExitException() {
        // given
        Command command = new Exit(view);

        // when
        try {
            command.process();
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }

        // then
//        assertEquals("До скорой встречи!\n", view.getContent());
        // throws ExitException
    }
}
