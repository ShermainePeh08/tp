package seedu.address.logic.parser;

import java.util.Arrays;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object.
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    public static final String MESSAGE_INVALID_CONFIRMATION_FLAG =
            "Invalid format. The '-y' flag must be standalone.\n"
                    + "Example: clear -y";

    public static final String CLEAR_CONFIRMATION_FLAG = "-y";

    public static final boolean REQUIRE_CONFIRMATION = true;

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ClearCommand parse(String args) throws ParseException {
        String argsTrimmed = args.trim();

        if (argsTrimmed.isEmpty()) {
            return new ClearCommand(REQUIRE_CONFIRMATION);
        }

        String[] tokens = argsTrimmed.split(ParserUtil.SEPARATOR_SPACE);
        boolean hasConfirmFlag = containsConfirmationFlag(tokens);

        return new ClearCommand(!hasConfirmFlag);
    }

    private boolean containsConfirmationFlag(String[] tokens) throws ParseException {
        boolean hasWronglyFormedFlag = Arrays.stream(tokens)
                .anyMatch(this::isMalformedConfirmationFlag);
        if (hasWronglyFormedFlag) {
            throw new ParseException(MESSAGE_INVALID_CONFIRMATION_FLAG);
        }
        return Arrays.asList(tokens).contains(CLEAR_CONFIRMATION_FLAG);
    }

    private boolean isMalformedConfirmationFlag(String token) {
        return token.startsWith(CLEAR_CONFIRMATION_FLAG)
                && !token.equals(CLEAR_CONFIRMATION_FLAG);
    }
}

