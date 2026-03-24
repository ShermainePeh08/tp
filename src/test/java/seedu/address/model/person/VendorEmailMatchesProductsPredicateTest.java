package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.product.Product;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ProductBuilder;

public class VendorEmailMatchesProductsPredicateTest {

    @Test
    public void constructor_nullProducts_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VendorEmailMatchesProductsPredicate(null));
    }

    @Test
    public void equals() {
        Product firstProduct = new ProductBuilder()
                .withIdentifier("SKU-101")
                .withVendorEmail("alice@example.com")
                .build();
        Product secondProduct = new ProductBuilder()
                .withIdentifier("SKU-102")
                .withVendorEmail("bob@example.com")
                .build();

        List<Product> firstProductList = Collections.singletonList(firstProduct);
        List<Product> secondProductList = Arrays.asList(firstProduct, secondProduct);

        VendorEmailMatchesProductsPredicate firstPredicate =
                new VendorEmailMatchesProductsPredicate(firstProductList);
        VendorEmailMatchesProductsPredicate secondPredicate =
                new VendorEmailMatchesProductsPredicate(secondProductList);

        assertTrue(firstPredicate.equals(firstPredicate));

        VendorEmailMatchesProductsPredicate firstPredicateCopy =
                new VendorEmailMatchesProductsPredicate(firstProductList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personEmailMatchesProductVendor_returnsTrue() {
        Person person = new PersonBuilder().withEmail("alice@example.com").build();
        Product matchingProduct = new ProductBuilder()
                .withIdentifier("SKU-201")
                .withVendorEmail("alice@example.com")
                .build();

        VendorEmailMatchesProductsPredicate predicate =
                new VendorEmailMatchesProductsPredicate(Collections.singletonList(matchingProduct));

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personEmailMismatchOrMissingVendorOrArchived_returnsFalse() {
        Person activePerson = new PersonBuilder().withEmail("alice@example.com").build();
        Person archivedPerson = activePerson.archive();

        Product mismatchedVendor = new ProductBuilder()
                .withIdentifier("SKU-202")
                .withVendorEmail("bob@example.com")
                .build();
        Product noVendor = new ProductBuilder()
                .withIdentifier("SKU-203")
                .withoutVendorEmail()
                .build();
        Product archivedMatchingVendor = new ProductBuilder()
                .withIdentifier("SKU-204")
                .withVendorEmail("alice@example.com")
                .build()
                .archive();

        VendorEmailMatchesProductsPredicate predicate =
                new VendorEmailMatchesProductsPredicate(List.of(mismatchedVendor, noVendor, archivedMatchingVendor));

        assertFalse(predicate.test(activePerson));
        assertFalse(predicate.test(archivedPerson));
    }

    @Test
    public void test_emptyProductList_returnsFalse() {
        Person person = new PersonBuilder().withEmail("alice@example.com").build();
        VendorEmailMatchesProductsPredicate predicate =
                new VendorEmailMatchesProductsPredicate(Collections.emptyList());

        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        Product product = new ProductBuilder()
                .withIdentifier("SKU-205")
                .withVendorEmail("alice@example.com")
                .build();
        VendorEmailMatchesProductsPredicate predicate =
                new VendorEmailMatchesProductsPredicate(Collections.singletonList(product));

        String output = predicate.toString();
        assertTrue(output.contains("products=["));
        assertTrue(output.contains("SKU-205"));
    }
}
