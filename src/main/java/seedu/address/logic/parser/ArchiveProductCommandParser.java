package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ArchiveProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ArchiveProductCommand} object.
 */
public class ArchiveProductCommandParser implements Parser<ArchiveProductCommand> {

    private static final String MESSAGE_INVALID_FORMAT =
            ArchiveProductCommand.MESSAGE_USAGE;

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveProductCommand
     * and returns an ArchiveProductCommand object for execution.
     *
     * @param args user input arguments
     * @return ArchiveProductCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ArchiveProductCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String identifier = args.trim();

        if (identifier.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        return new ArchiveProductCommand(identifier);
    }
}
