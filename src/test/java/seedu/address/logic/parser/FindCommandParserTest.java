package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsScoredPredicate;
import seedu.address.model.person.PersonTagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // BV: empty input should be rejected.
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // EP: whitespace-only input belongs to the same invalid partition after trimming.
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // EP: default name-search mode with valid keywords.
        FindCommand expectedFindCommand =
            new FindCommand(new NameContainsKeywordsScoredPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // BV: irregular internal and surrounding whitespace should normalize to the same tokens.
        assertParseSuccess(parser, " \n Alice\t Bob", expectedFindCommand);
    }

    @Test
    public void parse_tagMode_returnsFindCommand() {
        // EP: leading -t selects tag-search mode.
        FindCommand expectedFindCommand =
                new FindCommand(new PersonTagContainsKeywordsPredicate(Arrays.asList("vip", "priority")));
        assertParseSuccess(parser, "-t vip priority", expectedFindCommand);

        // BV: whitespace around flag and keywords should still parse correctly.
        assertParseSuccess(parser, " \n -t \t vip  priority", expectedFindCommand);
    }

    @Test
    public void parse_tagModeWithoutKeywords_throwsParseException() {
        // BV: tag mode requires at least one keyword; exactly one token (-t) is invalid.
        assertParseFailure(parser, "-t", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // BV: all tokens are mode flags, so effective keyword set is empty.
        assertParseFailure(parser, "-t -t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonLeadingTagFlag_returnsFindCommand() {
        // EP: -t can appear in non-leading position and still activates tag-search mode.
        FindCommand expectedFindCommand =
                new FindCommand(new PersonTagContainsKeywordsPredicate(Arrays.asList("vip", "priority")));
        assertParseSuccess(parser, "vip -t priority", expectedFindCommand);

        // EP: multiple occurrences of -t are treated as flags and ignored for keyword matching.
        assertParseSuccess(parser, "vip -t -t priority", expectedFindCommand);
    }

    @Test
    public void parse_escapedTagFlag_returnsFindCommandWithLiteralKeyword() {
        // EP: /-t is treated as a literal keyword in name mode, not as a tag-mode flag.
        FindCommand expectedNameModeCommand =
                new FindCommand(new NameContainsKeywordsScoredPredicate(Arrays.asList("-t", "vip")));
        assertParseSuccess(parser, "/-t vip", expectedNameModeCommand);

        // EP: /-t remains a literal keyword even when tag mode is activated by an unescaped -t.
        FindCommand expectedTagModeCommand =
                new FindCommand(new PersonTagContainsKeywordsPredicate(Arrays.asList("-t", "vip")));
        assertParseSuccess(parser, "-t /-t vip", expectedTagModeCommand);

        // BV: escaped literal plus one real flag gives exactly one effective tag keyword ("-t").
        FindCommand expectedSingleKeywordTagModeCommand =
            new FindCommand(new PersonTagContainsKeywordsPredicate(Arrays.asList("-t")));
        assertParseSuccess(parser, "/-t -t", expectedSingleKeywordTagModeCommand);
    }

}
