package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.view.Console;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.smart.andrey.leus.CRM.view.View;

import static junit.framework.TestCase.assertTrue;


import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;

public class HelpTest {

    private View view = Mockito.mock(Console.class);

    String text = "This programme provides next commands:\n" +
            "- 'connect' - connect to database\n" +
            "- 'list' - get list of databases;\n" +
            "- 'create' - create new database;\n" +
            "- 'drop' - delete the database;\n" +
            "- 'catalog' - get contain of tables (for example information about 'goods';\n" +
            "   -- in this partition you can: \n" +
            "      --- add, update or delete rows and columns;\n" +
            "      --- add, delete, clear table;\n" +
            "\n" +
            "- 'help' - get list of commands.\n" +
            "- 'exit' - escape from programme or return to main menu.\n";

    @Test
    public void testCanProcessHelpString() throws CRMException {
        // given
        Command command = new Help(view);

        // when
        boolean canProcess = command.canProcess("help");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessErrorString() throws CRMException {
        // given
        Command command = new Exit(view);

        // when
        boolean canProcess = command.canProcess("error");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcess() throws CRMException {
        // given
        Command command = new Help(view);

        // when
        command.process();

        // then
        verify(view).write(text);
    }
}
