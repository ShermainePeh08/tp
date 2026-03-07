package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RestoreCommand}.
 *
 * Expected format: restore vendor INDEX
 */
public class RestoreCommandParser implements Parser {

    private static final String MESSAGE_INVALID_FORMAT = "Usage: restore vendor INDEX";
    private static final String MESSAGE_INVALID_INDEX = "Index must be a positive integer.";

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

        if (!trimmedArgs.startsWith("vendor ")) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        String indexPart = trimmedArgs.substring(7).trim();

        if (indexPart.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        try {
            Index index = ParserUtil.parseIndex(indexPart);
            return new RestoreCommand(index);
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
    }
}
