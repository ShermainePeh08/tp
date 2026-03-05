package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UndoCommandIntegrationTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    /**
     * Test case: calling undo when no commits have been made should fail.
     */
    @Test
    public void execute_noUndoVersion_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();
        // No commits yet, should throw exception
        assertThrows(CommandException.class, () -> undoCommand.execute(model));
    }

    /**
     * Test case: after executing a command that modifies the model and commits, undo should restore previous state.
     */
    @Test
    public void execute_undoableCommandExecuted_restoresPreviousState() throws CommandException {
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        model.commitAddressBook();

        expectedModel.addPerson(validPerson);
        expectedModel.commitAddressBook();

        expectedModel.deletePerson(validPerson);

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        assertEquals(expectedModel.getAddressBook(), model.getAddressBook());
    }
}
