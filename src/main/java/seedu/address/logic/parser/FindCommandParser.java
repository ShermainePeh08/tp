package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NON_PREFIX_BEFORE_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameAndTagMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsScoredPredicate;
import seedu.address.model.person.PersonTagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String WHITESPACE_REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + args, PREFIX_NAME, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_NON_PREFIX_BEFORE_PREFIX + FindCommand.MESSAGE_USAGE);
        }

        List<String> nameKeywords = flattenKeywordValues(argMultimap.getAllValues(PREFIX_NAME));
        List<String> tagKeywords = flattenKeywordValues(argMultimap.getAllValues(PREFIX_TAG));

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (nameKeywords.isEmpty()) {
            return new FindCommand(new PersonTagContainsKeywordsPredicate(tagKeywords));
        }

        if (tagKeywords.isEmpty()) {
            return new FindCommand(new NameContainsKeywordsScoredPredicate(nameKeywords));
        }

        NameContainsKeywordsScoredPredicate namePredicate = new NameContainsKeywordsScoredPredicate(nameKeywords);
        PersonTagContainsKeywordsPredicate tagPredicate = new PersonTagContainsKeywordsPredicate(tagKeywords);
        return new FindCommand(new NameAndTagMatchesPredicate(namePredicate, tagPredicate));
    }

    private List<String> flattenKeywordValues(List<String> rawValues) {
        return rawValues.stream()
                .flatMap(value -> Stream.of(value.split(WHITESPACE_REGEX)))
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .toList();
    }

}
