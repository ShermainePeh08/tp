package seedu.address.model.person.warnings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class DuplicatePersonWarningTest {

    private static final Name NAME_ACME = new Name("Acme Supplies");
    private static final Address ADDRESS_ACME = new Address("10 Business Park");
    private static final Phone PHONE_ACME = new Phone("91234567");
    private static final String WARNING_PREFIX = "⚠";

    @Test
    public void constructor_withMessage_getWarningReturnsSameMessage() {
        // EP: non-null warning string is stored and returned as-is
        String warning = "warning message";

        DuplicatePersonWarning duplicatePersonWarning = new DuplicatePersonWarning(warning);

        assertEquals(warning, duplicatePersonWarning.getWarning());
    }

    @Test
    public void constructor_withNull_getWarningReturnsNull() {
        // EP: null warning is stored and returned as null
        DuplicatePersonWarning duplicatePersonWarning = new DuplicatePersonWarning(null);

        assertNull(duplicatePersonWarning.getWarning());
    }

    @Test
    public void formatNameWarning_validName_containsNameAndWarningSymbol() {
        // EP: valid name -> output contains the name and warning prefix
        String result = DuplicatePersonWarning.formatNameWarning(NAME_ACME);
        assertTrue(result.contains(NAME_ACME.toString()));
        assertTrue(result.contains(WARNING_PREFIX));
    }

    @Test
    public void formatNameWarning_validName_matchesExpectedFormat() {
        // EP: output exactly matches MESSAGE_SIMILAR_NAME template
        String expected = String.format(DuplicatePersonWarning.MESSAGE_SIMILAR_NAME, NAME_ACME);
        assertEquals(expected, DuplicatePersonWarning.formatNameWarning(NAME_ACME));
    }

    @Test
    public void formatAddressWarning_validNameAndAddress_containsNameAddressAndWarningSymbol() {
        // EP: valid name and address -> output contains both and warning prefix
        String result = DuplicatePersonWarning.formatAddressWarning(NAME_ACME, ADDRESS_ACME);
        assertTrue(result.contains(NAME_ACME.toString()));
        assertTrue(result.contains(ADDRESS_ACME.toString()));
        assertTrue(result.contains(WARNING_PREFIX));
    }

    @Test
    public void formatAddressWarning_validNameAndAddress_matchesExpectedFormat() {
        // EP: output exactly matches MESSAGE_SIMILAR_ADDRESS template
        String expected = String.format(DuplicatePersonWarning.MESSAGE_SIMILAR_ADDRESS, NAME_ACME, ADDRESS_ACME);
        assertEquals(expected, DuplicatePersonWarning.formatAddressWarning(NAME_ACME, ADDRESS_ACME));
    }

    @Test
    public void formatPhoneWarning_validNameAndPhone_containsNamePhoneAndWarningSymbol() {
        // EP: valid name and phone -> output contains both and warning prefix
        String result = DuplicatePersonWarning.formatPhoneWarning(NAME_ACME, PHONE_ACME);
        assertTrue(result.contains(NAME_ACME.toString()));
        assertTrue(result.contains(PHONE_ACME.toString()));
        assertTrue(result.contains(WARNING_PREFIX));
    }

    @Test
    public void formatPhoneWarning_validNameAndPhone_matchesExpectedFormat() {
        // EP: output exactly matches MESSAGE_SIMILAR_PHONE template
        String expected = String.format(DuplicatePersonWarning.MESSAGE_SIMILAR_PHONE, NAME_ACME, PHONE_ACME);
        assertEquals(expected, DuplicatePersonWarning.formatPhoneWarning(NAME_ACME, PHONE_ACME));
    }

}

