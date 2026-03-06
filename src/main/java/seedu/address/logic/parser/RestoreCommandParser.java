package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RestoreCommand}.
 *
 * Expected format: restore vendor INDEX
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a RestoreCommand object for execution.
     *
     * @param args user input arguments
     * @return RestoreCommand object
     * @throws ParseException if the input does not conform to the expected format
     */
    public RestoreCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        if (!trimmedArgs.startsWith("vendor")) {
            throw new ParseException("Usage: restore vendor INDEX");
        }

        String indexPart = trimmedArgs.substring(6).trim();

        Index index = ParserUtil.parseIndex(indexPart);

        return new RestoreCommand(index);
    }
}