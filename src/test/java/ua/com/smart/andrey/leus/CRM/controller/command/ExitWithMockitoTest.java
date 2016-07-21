package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.view.Console;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.smart.andrey.leus.CRM.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;


public class ExitWithMockitoTest {

    private View view = Mockito.mock(Console.class);

    @Test
    public void testCanProcessExitString() throws CRMException {
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("exit");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessQweString() throws CRMException {
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_thowsExitException() throws CRMException {
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
        Mockito.verify(view).write("See you again!\n");
    }
}
