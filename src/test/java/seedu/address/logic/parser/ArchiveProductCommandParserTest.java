package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ArchiveProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ArchiveProductCommandParserTest {

    private final ArchiveProductCommandParser parser = new ArchiveProductCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveProductCommand() throws Exception {
        ArchiveProductCommand command = parser.parse("p1");
        assertTrue(command instanceof ArchiveProductCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
