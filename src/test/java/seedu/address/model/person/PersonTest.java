package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    // Phone: shares "123456" overlap to trigger similar phone match
    private static final String PHONE_VALID_A = "91234567";
    private static final String PHONE_VALID_NO_OVERLAP = "80000000";
    private static final String PHONE_VALID_OVERLAP = "00123456";

    // Names: share one word ("Supplies") to trigger similar name match
    private static final String NAME_SHARED_WORD_A = "Acme Supplies";
    private static final String NAME_SHARED_WORD_B = "Global Supplies";
    private static final String NAME_NO_SHARED_WORD = "Tan Ah Kow";

    // Addresses: ADDRESS_PARTIAL is fully contained within ADDRESS_FULL
    private static final String ADDRESS_FULL = "123 Woodlands Avenue 5";
    private static final String ADDRESS_PARTIAL = "Woodlands";
    private static final String ADDRESS_UNRELATED = "99 Tuas Road";

    // -------------------- isSimilarNameTo Tests --------------------

    @Test
    public void isSimilarNameTo_returnsTrue() {
        // EP: names share at least one word
        assertTrue(new PersonBuilder().withName(NAME_SHARED_WORD_A).build()
                .isSimilarNameTo(new PersonBuilder().withName(NAME_SHARED_WORD_B).build()));

        // EP: identical names
        assertTrue(new PersonBuilder().withName(NAME_SHARED_WORD_A).build()
                .isSimilarNameTo(new PersonBuilder().withName(NAME_SHARED_WORD_A).build()));
    }

    @Test
    public void isSimilarNameTo_returnsFalse() {
        // EP: names share no words
        assertFalse(new PersonBuilder().withName(NAME_SHARED_WORD_A).build()
                .isSimilarNameTo(new PersonBuilder().withName(NAME_NO_SHARED_WORD).build()));
    }

    // -------------------- isSimilarAddressTo Tests --------------------

    @Test
    public void isSimilarAddressTo_returnsTrue() {
        // EP: one address fully contains the other
        assertTrue(new PersonBuilder().withAddress(ADDRESS_FULL).build()
                .isSimilarAddressTo(new PersonBuilder().withAddress(ADDRESS_PARTIAL).build()));

        // EP: identical addresses
        assertTrue(new PersonBuilder().withAddress(ADDRESS_FULL).build()
                .isSimilarAddressTo(new PersonBuilder().withAddress(ADDRESS_FULL).build()));
    }

    @Test
    public void isSimilarAddressTo_returnsFalse() {
        // EP: neither address contains the other
        assertFalse(new PersonBuilder().withAddress(ADDRESS_FULL).build()
                .isSimilarAddressTo(new PersonBuilder().withAddress(ADDRESS_UNRELATED).build()));
    }

    // -------------------- isSimilarPhoneTo Tests --------------------
    @Test
    public void isSimilarPhoneTo_returnsFalse() {
        // EP: both valid length but no contiguous digit overlap
        assertFalse(new PersonBuilder().withPhone(PHONE_VALID_A).build()
                .isSimilarPhoneTo(new PersonBuilder().withPhone(PHONE_VALID_NO_OVERLAP).build()));
    }

    @Test
    public void isSimilarPhoneTo_returnsTrue() {
        // EP: phones share a contiguous digit sequence of sufficient length
        assertTrue(new PersonBuilder().withPhone(PHONE_VALID_A).build()
                .isSimilarPhoneTo(new PersonBuilder().withPhone(PHONE_VALID_OVERLAP).build()));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // EP: same reference -> true
        assertTrue(ALICE.isSamePerson(ALICE));

        // EP: same email, all other attributes different -> true (email is the unique identifier)
        assertTrue(ALICE.isSamePerson(new PersonBuilder(ALICE)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build()));

        // EP: null -> false
        assertFalse(ALICE.isSamePerson(null));

        // EP: different email -> false (even if other fields match)
        assertFalse(ALICE.isSamePerson(new PersonBuilder(ALICE)
                .withEmail(VALID_EMAIL_BOB)
                .build()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = ALICE.getName()
                + ", Phone: " + ALICE.getPhone()
                + ", Email: " + ALICE.getEmail()
                + ", Address: " + ALICE.getAddress();

        if (!ALICE.getTags().isEmpty()) {
            expected += ", Tags: " + ALICE.getTags();
        }

        assertEquals(expected, ALICE.toString());
    }

}
