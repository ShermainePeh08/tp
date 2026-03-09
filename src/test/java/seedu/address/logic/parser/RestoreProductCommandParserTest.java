package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RestoreProductCommand;

public class RestoreProductCommandParserTest {

    private final RestoreProductCommandParser parser = new RestoreProductCommandParser();

    @Test
    public void parse_validArgs_returnsRestoreProductCommand() throws Exception {
        RestoreProductCommand command = parser.parse("p1");
        assertTrue(command instanceof RestoreProductCommand);
    }

    @Test
    public void parse_emptyArgs_returnsRestoreProductCommand() throws Exception {
        RestoreProductCommand command = parser.parse("");
        assertTrue(command instanceof RestoreProductCommand);
    }
}
