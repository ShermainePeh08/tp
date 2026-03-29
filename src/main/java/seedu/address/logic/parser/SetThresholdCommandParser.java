package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetThresholdCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.RestockThreshold;

/**
 * Parses input arguments and creates a SetThresholdCommand object.
 */
public class SetThresholdCommandParser implements Parser<SetThresholdCommand> {

    @Override
    public SetThresholdCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetThresholdCommand.MESSAGE_USAGE));
        }

        String[] tokens = trimmedArgs.split(ParserUtil.SEPARATOR_SPACE);
        if (tokens.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetThresholdCommand.MESSAGE_USAGE));
        }

        RestockThreshold threshold = ParserUtil.parseThreshold(tokens[0]).getValue();
        return new SetThresholdCommand(threshold);
    }
}
