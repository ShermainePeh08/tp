package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RestoreCommand}.
 */
public class RestoreCommandParser implements Parser {

    /** Error message for incorrect command format */
    private static final String MESSAGE_INVALID_FORMAT = "Usage: restore INDEX";

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a RestoreCommand object for execution.
     *
     * @param args user input arguments
     * @return RestoreCommand object
     * @throws ParseException if the input does not conform to the expected format
     */
    @Override
    public RestoreCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new RestoreCommand(index);
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
    }
}
