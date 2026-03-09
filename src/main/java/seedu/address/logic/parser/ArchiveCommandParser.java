package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ArchiveCommand}.
 */
public class ArchiveCommandParser implements Parser {

    /** Error message for incorrect command format */
    private static final String MESSAGE_INVALID_FORMAT = "Usage: archive INDEX";

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveCommand
     * and returns an ArchiveCommand object for execution.
     *
     * @param args user input arguments
     * @return ArchiveCommand object
     * @throws ParseException if the input does not conform to the expected format
     */
    @Override
    public ArchiveCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new ArchiveCommand(index);
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
    }
}
