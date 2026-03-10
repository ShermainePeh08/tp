package seedu.address.logic.parser;

import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ArchiveCommand}.
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    /** Error message for incorrect command format */
    private static final String MESSAGE_INVALID_FORMAT = "Email must be provided.";

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

        String email = args.trim();

        if (email.isEmpty()) {
            throw new ParseException(ArchiveCommand.MESSAGE_USAGE);
        }

        return new ArchiveCommand(email);
    }
}
