package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RestoreCommand;

public class RestoreCommandParserTest {

    private final RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_validArgs_returnsRestoreCommand() throws Exception {
        RestoreCommand command = parser.parse("alexyeoh@example.com");
        assertTrue(command instanceof RestoreCommand);
    }

    @Test
    public void parse_emptyArgs_returnsRestoreCommand() throws Exception {
        RestoreCommand command = parser.parse("");
        assertTrue(command instanceof RestoreCommand);
    }
}
