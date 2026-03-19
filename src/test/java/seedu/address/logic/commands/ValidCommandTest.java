package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ValidCommandTest {

    @Test
    public void getCommandWord_allTypes_returnsCorrectWord() {
        assertEquals(AddCommand.COMMAND_WORD, ValidCommand.ADD.commandWord);
        assertEquals(AddProductCommand.COMMAND_WORD, ValidCommand.ADDPRODUCT.commandWord);
        assertEquals(ArchiveCommand.COMMAND_WORD, ValidCommand.ARCHIVE.commandWord);
        assertEquals(ArchiveProductCommand.COMMAND_WORD, ValidCommand.ARCHIVEPRODUCT.commandWord);
        assertEquals(ClearCommand.COMMAND_WORD, ValidCommand.CLEAR.commandWord);
        assertEquals(DeleteCommand.COMMAND_WORD, ValidCommand.DELETE.commandWord);
        assertEquals(EditCommand.COMMAND_WORD, ValidCommand.EDIT.commandWord);
        assertEquals(ExitCommand.COMMAND_WORD, ValidCommand.EXIT.commandWord);
        assertEquals(FindCommand.COMMAND_WORD, ValidCommand.FIND.commandWord);
        assertEquals(HelpCommand.COMMAND_WORD, ValidCommand.HELP.commandWord);
        assertEquals(ListCommand.COMMAND_WORD, ValidCommand.LIST.commandWord);
        assertEquals(RedoCommand.COMMAND_WORD, ValidCommand.REDO.commandWord);
        assertEquals(RestoreCommand.COMMAND_WORD, ValidCommand.RESTORE.commandWord);
        assertEquals(RestoreProductCommand.COMMAND_WORD, ValidCommand.RESTOREPRODUCT.commandWord);
        assertEquals(UndoCommand.COMMAND_WORD, ValidCommand.UNDO.commandWord);
    }

    @Test
    public void valueOf_validName_returnsCorrectEnum() {
        assertEquals(ValidCommand.ADD, ValidCommand.valueOf("ADD"));
        assertEquals(ValidCommand.UNDO, ValidCommand.valueOf("UNDO"));
    }

    @Test
    public void valueOf_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ValidCommand.valueOf("INVALID"));
    }

    @Test
    public void isValidCommand_validAliasCommandWord_returnsTrue() {
        assertTrue(ValidCommand.isValidCommand(AddCommand.COMMAND_WORD));
        assertTrue(ValidCommand.isValidCommand(ListCommand.COMMAND_WORD));
        assertTrue(ValidCommand.isValidCommand(UndoCommand.COMMAND_WORD));
    }

    @Test
    public void isValidAliasCommand_emptyString_returnsFalse() {
        assertFalse(ValidCommand.isValidCommand(""));
    }

    @Test
    public void isValidCommand_invalidAliasCommandWord_returnsFalse() {
        assertFalse(ValidCommand.isValidCommand("invalidCommand"));
    }

    @Test
    public void isValidCommand_validAliasCommandWordWrongCase_returnsFalse() {
        assertFalse(ValidCommand.isValidCommand(AddCommand.COMMAND_WORD.toUpperCase()));
    }
}
