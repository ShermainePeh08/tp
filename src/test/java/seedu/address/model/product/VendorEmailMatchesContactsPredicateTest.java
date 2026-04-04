package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalProducts.IPAD;
import static seedu.address.testutil.TypicalProducts.OIL;
import static seedu.address.testutil.TypicalProducts.RICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ProductBuilder;

public class VendorEmailMatchesContactsPredicateTest {

    @Test
    public void equals() {
        List<Person> singleContact = Collections.singletonList(ALICE);
        List<Person> twoContacts = Arrays.asList(ALICE, BOB);

        VendorEmailMatchesContactsPredicate singlePredicate =
                new VendorEmailMatchesContactsPredicate(singleContact);
        VendorEmailMatchesContactsPredicate twoPredicate =
                new VendorEmailMatchesContactsPredicate(twoContacts);

        // EP: same reference -> true
        assertTrue(singlePredicate.equals(singlePredicate));

        // EP: same contact list contents -> true
        assertTrue(singlePredicate.equals(new VendorEmailMatchesContactsPredicate(singleContact)));

        // EP: different type -> false
        assertFalse(singlePredicate.equals(1));

        // EP: null -> false
        assertFalse(singlePredicate.equals(null));

        // EP: different contact list -> false
        assertFalse(singlePredicate.equals(twoPredicate));
    }

    @Test
    public void test_vendorEmailMatchesContacts_returnsTrue() {
        // EP: product vendor email matches the one contact in the list
        VendorEmailMatchesContactsPredicate predicate =
                new VendorEmailMatchesContactsPredicate(Collections.singletonList(ALICE));

        Product matchingProduct = new ProductBuilder(IPAD)
                .withVendorEmail(ALICE.getEmail().toString())
                .build();

        assertTrue(predicate.test(matchingProduct));
    }

    @Test
    public void test_vendorEmailMatchesOneOfMultipleContacts_returnsTrue() {
        // EP: contact list has multiple entries; product matches the second one
        VendorEmailMatchesContactsPredicate predicate =
                new VendorEmailMatchesContactsPredicate(Arrays.asList(ALICE, BOB));

        Product product = new ProductBuilder(IPAD)
                .withVendorEmail(BOB.getEmail().toString())
                .build();

        assertTrue(predicate.test(product));
    }

    @Test
    public void test_vendorEmailDoesNotMatchOrIsMissingOrArchived_returnsFalse() {
        VendorEmailMatchesContactsPredicate predicate =
                new VendorEmailMatchesContactsPredicate(Collections.singletonList(ALICE));

        // EP: product has a vendor email, but it matches none of the contacts
        assertFalse(predicate.test(new ProductBuilder(IPAD)
                .withVendorEmail(BOB.getEmail().toString())
                .build()));

        // EP: product has no vendor email
        assertFalse(predicate.test(new ProductBuilder(RICE)
                .withoutVendorEmail()
                .build()));

        // EP: vendor email matches a contact, but product is archived -> early return false
        assertFalse(predicate.test(new ProductBuilder(OIL)
                .withVendorEmail(ALICE.getEmail().toString())
                .build()
                .archive()));
    }

    @Test
    public void test_emptyContactList_returnsFalse() {
        // EP: contact list is empty -> no email can ever match
        VendorEmailMatchesContactsPredicate predicate =
                new VendorEmailMatchesContactsPredicate(Collections.emptyList());

        Product product = new ProductBuilder(IPAD)
                .withVendorEmail(ALICE.getEmail().toString())
                .build();

        assertFalse(predicate.test(product));
    }

    @Test
    public void toStringMethod() {
        Person alice = new PersonBuilder().withEmail("alice@example.com").build();
        VendorEmailMatchesContactsPredicate predicate =
                new VendorEmailMatchesContactsPredicate(Collections.singletonList(alice));

        String output = predicate.toString();
        assertTrue(output.contains("contacts=["));
        assertTrue(output.contains("alice@example.com"));
    }
}

