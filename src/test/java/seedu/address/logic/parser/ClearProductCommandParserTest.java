package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONFIRMATION_FLAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalAliases.getTypicalAliases;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProducts.getTypicalInventory;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearProductCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VendorVault;

public class ClearProductCommandParserTest {

    private final ClearProductCommandParser parser = new ClearProductCommandParser();

    @Test
    public void parse_validArgs_returnsCommand() throws Exception {
        ClearProductCommand command = parser.parse("");
        assertNotNull(command);
    }

    @Test
    public void parse_standaloneFlag_skipsConfirmation() throws Exception {
        Model model = new ModelManager(
                new VendorVault(getTypicalAddressBook(), getTypicalInventory()), new UserPrefs(), getTypicalAliases());

        ClearProductCommand command = parser.parse("-y");
        CommandResult result = command.execute(model);

        assertEquals(ClearProductCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(model.getInventory().getProductList().isEmpty());
    }

    @Test
    public void parse_malformedFlagAttached_throwsParseException() {
        assertParseFailure(parser, "-yabc", MESSAGE_INVALID_CONFIRMATION_FLAG);
        assertParseFailure(parser, "-y1", MESSAGE_INVALID_CONFIRMATION_FLAG);
    }

    @Test
    public void parse_malformedFlagInToken_throwsParseException() {
        assertParseFailure(parser, "foo -ybar", MESSAGE_INVALID_CONFIRMATION_FLAG);
    }

}
