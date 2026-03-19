package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ConfirmationFlagIndicator.containsConfirmationFlag;
import static seedu.address.logic.parser.ConfirmationFlagIndicator.removeConfirmationFlag;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ConfirmationFlagIndicatorTest {
    private static final String FLAG = "-y";
    private static final String EXCEPTION_MESSAGE = "Invalid confirmation flag.";

    @Test
    public void containsConfirmationFlag_flagPresent_returnsTrue() throws ParseException {
        String[] tokens = {"delete", "1", FLAG};
        assertTrue(containsConfirmationFlag(tokens, FLAG, EXCEPTION_MESSAGE));
    }

    @Test
    public void containsConfirmationFlag_flagAbsent_returnsFalse() throws ParseException {
        String[] tokens = {"delete", "1"};
        assertFalse(containsConfirmationFlag(tokens, FLAG, EXCEPTION_MESSAGE));
    }

    @Test
    public void containsConfirmationFlag_emptyTokens_returnsFalse() throws ParseException {
        String[] tokens = {};
        assertFalse(containsConfirmationFlag(tokens, FLAG, EXCEPTION_MESSAGE));
    }

    @Test
    public void containsConfirmationFlag_malformedFlag_throwsParseException() {
        String[] tokens = {"delete", "1", FLAG + "Extra"};
        assertThrows(ParseException.class, () -> containsConfirmationFlag(tokens, FLAG, EXCEPTION_MESSAGE));
    }

    @Test
    public void removeConfirmationFlag_flagPresent_removesFlag() {
        String[] tokens = {"delete", "1", FLAG};
        assertEquals("delete 1", removeConfirmationFlag(tokens, FLAG));
    }

    @Test
    public void removeConfirmationFlag_flagAbsent_returnsAllTokens() {
        String[] tokens = {"delete", "1"};
        assertEquals("delete 1", removeConfirmationFlag(tokens, FLAG));
    }

    @Test
    public void removeConfirmationFlag_flagOnly_returnsEmptyString() {
        String[] tokens = {FLAG};
        assertEquals("", removeConfirmationFlag(tokens, FLAG));
    }

    @Test
    public void removeConfirmationFlag_emptyTokens_returnsEmptyString() {
        String[] tokens = {};
        assertEquals("", removeConfirmationFlag(tokens, FLAG));
    }

    @Test
    public void removeConfirmationFlag_malformedFlagToken_isNotRemoved() {
        String malformed = FLAG + "XYZ";
        String[] tokens = {"delete", malformed};
        assertEquals("delete " + malformed, removeConfirmationFlag(tokens, FLAG));
    }
}
