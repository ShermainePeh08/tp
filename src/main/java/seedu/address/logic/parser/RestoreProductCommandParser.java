package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.RestoreProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RestoreProductCommand} object.
 */
public class RestoreProductCommandParser implements Parser<RestoreProductCommand> {

    private static final String MESSAGE_INVALID_FORMAT =
            RestoreProductCommand.MESSAGE_USAGE;

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreProductCommand
     * and returns a RestoreProductCommand object for execution.
     *
     * @param args user input arguments
     * @return RestoreProductCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RestoreProductCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String identifier = args.trim();

        if (identifier.isEmpty()) {
            return new RestoreProductCommand(null);
        }

        return new RestoreProductCommand(identifier);
    }
}
