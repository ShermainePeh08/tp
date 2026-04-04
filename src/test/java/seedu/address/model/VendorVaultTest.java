package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProducts.getTypicalInventory;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ProductBuilder;

public class VendorVaultTest {

    private static final String NAME_ACME_PERSON = "Acme Supplies";
    private static final String NAME_SIMILAR_PERSON = "Acme Partners";
    private static final String NAME_OTHER_PERSON = "Other";
    private static final String PHONE_ACME_PERSON = "91234567";
    private static final String PHONE_SIMILAR_PERSON = "00123456";
    private static final String PHONE_OTHER_PERSON = "80000000";
    private static final String EMAIL_ACME_PERSON = "acme@example.com";
    private static final String EMAIL_OTHER_PERSON = "other@example.com";
    private static final String ADDRESS_ACME_PERSON = "10 Business Park";
    private static final String ADDRESS_SIMILAR_PERSON = "10 Business";
    private static final String ADDRESS_OTHER_PERSON = "99 Other Street";

    private static final String SKU_WIRELESS_PRODUCT = "SKU-8001";
    private static final String SKU_OTHER_PRODUCT = "SKU-8002";
    private static final String NAME_WIRELESS_PRODUCT = "Wireless Mouse";
    private static final String NAME_SIMILAR_PRODUCT = "Wireless Keyboard";

    private final VendorVault vendorVault = new VendorVault();

    @Test
    public void constructor() {
        assertEquals(0, vendorVault.getPersonList().size());
        assertEquals(0, vendorVault.getProductList().size());
    }

    @Test
    public void constructor_withReadOnlyVendorVault_copiesData() {
        VendorVault source = createTypicalVendorVault();
        VendorVault copied = new VendorVault(source);

        assertEquals(source, copied);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> vendorVault.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyVendorVault_replacesData() {
        VendorVault newData = createTypicalVendorVault();
        vendorVault.resetData(newData);

        assertEquals(newData, vendorVault);
    }

    @Test
    public void setAddressBook_replacesOnlyAddressBookData() {
        VendorVault vendorVault = new VendorVault();
        vendorVault.setInventory(getTypicalInventory());

        vendorVault.setAddressBook(getTypicalAddressBook());

        assertEquals(getTypicalAddressBook().getPersonList(), vendorVault.getPersonList());
        assertEquals(getTypicalInventory().getProductList(), vendorVault.getProductList());
    }

    @Test
    public void setInventory_replacesOnlyInventoryData() {
        VendorVault vendorVault = new VendorVault();
        vendorVault.setAddressBook(getTypicalAddressBook());

        vendorVault.setInventory(getTypicalInventory());

        assertEquals(getTypicalAddressBook().getPersonList(), vendorVault.getPersonList());
        assertEquals(getTypicalInventory().getProductList(), vendorVault.getProductList());
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        vendorVault.resetData(createTypicalVendorVault());
        assertThrows(UnsupportedOperationException.class, () -> vendorVault.getPersonList().remove(0));
    }

    @Test
    public void getProductList_modifyList_throwsUnsupportedOperationException() {
        vendorVault.resetData(createTypicalVendorVault());
        assertThrows(UnsupportedOperationException.class, () -> vendorVault.getProductList().remove(0));
    }

    @Test
    public void getAddressBook_returnsAddressBookData() {
        vendorVault.setAddressBook(getTypicalAddressBook());

        assertEquals(getTypicalAddressBook(), vendorVault.getAddressBook());
    }

    @Test
    public void getInventory_returnsInventoryData() {
        vendorVault.setInventory(getTypicalInventory());

        assertEquals(getTypicalInventory(), vendorVault.getInventory());
    }

    // ---------------- Person Tests ----------------

    @Test
    public void findSimilarNameMatch_matchExists_returnsMatchingPerson() {
        // EP: candidate name is similar to existing; exclude = null -> match returned
        Person existing = buildPerson(NAME_ACME_PERSON, PHONE_ACME_PERSON, EMAIL_ACME_PERSON, ADDRESS_ACME_PERSON);
        VendorVault vault = createVaultWithPerson(existing);
        Person candidate = buildPerson(NAME_SIMILAR_PERSON,
                PHONE_OTHER_PERSON, EMAIL_OTHER_PERSON, ADDRESS_OTHER_PERSON);

        assertEquals(Optional.of(existing), vault.findSimilarNameMatch(candidate, null));
    }

    @Test
    public void findSimilarNameMatch_matchingPersonExcluded_returnsEmpty() {
        // EP: candidate name is similar but the matching person is excluded -> empty
        Person existing = buildPerson(NAME_ACME_PERSON, PHONE_ACME_PERSON, EMAIL_ACME_PERSON, ADDRESS_ACME_PERSON);
        VendorVault vault = createVaultWithPerson(existing);
        Person candidate = buildPerson(NAME_SIMILAR_PERSON,
                PHONE_OTHER_PERSON, EMAIL_OTHER_PERSON, ADDRESS_OTHER_PERSON);

        assertEquals(Optional.empty(), vault.findSimilarNameMatch(candidate, existing));
    }

    @Test
    public void findSimilarAddressMatch_matchExists_returnsMatchingPerson() {
        // EP: candidate address partially matches existing address; exclude = null -> match returned
        Person existing = buildPerson(NAME_ACME_PERSON, PHONE_ACME_PERSON, EMAIL_ACME_PERSON, ADDRESS_ACME_PERSON);
        VendorVault vault = createVaultWithPerson(existing);
        Person candidate = buildPerson(NAME_OTHER_PERSON,
                PHONE_OTHER_PERSON, EMAIL_OTHER_PERSON, ADDRESS_SIMILAR_PERSON);

        assertEquals(Optional.of(existing), vault.findSimilarAddressMatch(candidate, null));
    }

    @Test
    public void findSimilarAddressMatch_matchingPersonExcluded_returnsEmpty() {
        // EP: candidate address partially matches but the matching person is excluded -> empty
        Person existing = buildPerson(NAME_ACME_PERSON, PHONE_ACME_PERSON, EMAIL_ACME_PERSON, ADDRESS_ACME_PERSON);
        VendorVault vault = createVaultWithPerson(existing);
        Person candidate = buildPerson(NAME_OTHER_PERSON,
                PHONE_OTHER_PERSON, EMAIL_OTHER_PERSON, ADDRESS_SIMILAR_PERSON);

        assertEquals(Optional.empty(), vault.findSimilarAddressMatch(candidate, existing));
    }

    @Test
    public void findSimilarPhoneMatch_matchExists_returnsMatchingPerson() {
        // EP: candidate phone shares digits with existing; exclude = null -> match returned
        Person existing = buildPerson(NAME_ACME_PERSON, PHONE_ACME_PERSON, EMAIL_ACME_PERSON, ADDRESS_ACME_PERSON);
        VendorVault vault = createVaultWithPerson(existing);
        Person candidate = buildPerson(NAME_OTHER_PERSON,
                PHONE_SIMILAR_PERSON, EMAIL_OTHER_PERSON, ADDRESS_OTHER_PERSON);

        assertEquals(Optional.of(existing), vault.findSimilarPhoneMatch(candidate, null));
    }

    @Test
    public void findSimilarPhoneMatch_matchingPersonExcluded_returnsEmpty() {
        // EP: candidate phone shares digits but the matching person is excluded -> empty
        Person existing = buildPerson(NAME_ACME_PERSON, PHONE_ACME_PERSON, EMAIL_ACME_PERSON, ADDRESS_ACME_PERSON);
        VendorVault vault = createVaultWithPerson(existing);
        Person candidate = buildPerson(NAME_OTHER_PERSON,
                PHONE_SIMILAR_PERSON, EMAIL_OTHER_PERSON, ADDRESS_OTHER_PERSON);

        assertEquals(Optional.empty(), vault.findSimilarPhoneMatch(candidate, existing));
    }

    // ---------------- Product Tests ----------------

    @Test
    public void findSimilarNameMatch_productMatchExists_returnsMatchingProduct() {
        // EP: candidate product name is similar to existing; exclude = null -> match returned
        Product existing = buildProduct(SKU_WIRELESS_PRODUCT, NAME_WIRELESS_PRODUCT);
        VendorVault vault = createVaultWithProduct(existing);
        Product candidate = buildProduct(SKU_OTHER_PRODUCT, NAME_SIMILAR_PRODUCT);

        assertEquals(Optional.of(existing), vault.findSimilarNameMatch(candidate, null));
    }

    @Test
    public void findSimilarNameMatch_matchingProductExcluded_returnsEmpty() {
        // EP: candidate product name is similar but the matching product is excluded -> empty
        Product existing = buildProduct(SKU_WIRELESS_PRODUCT, NAME_WIRELESS_PRODUCT);
        VendorVault vault = createVaultWithProduct(existing);
        Product candidate = buildProduct(SKU_OTHER_PRODUCT, NAME_SIMILAR_PRODUCT);

        assertEquals(Optional.empty(), vault.findSimilarNameMatch(candidate, existing));
    }

    @Test
    public void toStringMethod() {
        String expected = VendorVault.class.getCanonicalName() + "{addressBook=" + vendorVault.getAddressBook()
                + ", inventory=" + vendorVault.getInventory() + "}";
        assertEquals(expected, vendorVault.toString());
    }

    @Test
    public void equals() {
        VendorVault expected = createTypicalVendorVault();
        VendorVault copy = createTypicalVendorVault();

        assertTrue(expected.equals(copy));
        assertTrue(expected.equals(expected));
        assertFalse(expected.equals(null));
        assertFalse(expected.equals(1));

        VendorVault differentAddressBook = createTypicalVendorVault();
        differentAddressBook.setAddressBook(new AddressBook());
        assertFalse(expected.equals(differentAddressBook));

        VendorVault differentInventory = createTypicalVendorVault();
        differentInventory.setInventory(new Inventory());
        assertFalse(expected.equals(differentInventory));
    }

    // ---------------- Helper methods ----------------

    private Person buildPerson(String name, String phone, String email, String address) {
        return new PersonBuilder()
                .withName(name)
                .withPhone(phone)
                .withEmail(email)
                .withAddress(address)
                .build();
    }

    private Product buildProduct(String identifier, String name) {
        return new ProductBuilder()
                .withIdentifier(identifier)
                .withName(name)
                .build();
    }

    private VendorVault createVaultWithPerson(Person person) {
        return createVendorVaultWithPersons(person);
    }

    private VendorVault createVaultWithProduct(Product product) {
        return createVendorVaultWithProducts(product);
    }

    private VendorVault createTypicalVendorVault() {
        VendorVault typicalVendorVault = new VendorVault();
        typicalVendorVault.setAddressBook(getTypicalAddressBook());
        typicalVendorVault.setInventory(getTypicalInventory());
        return typicalVendorVault;
    }

    private VendorVault createVendorVaultWithPersons(Person... persons) {
        VendorVault customVendorVault = new VendorVault();
        for (Person person : persons) {
            customVendorVault.getAddressBook().addPerson(person);
        }
        return customVendorVault;
    }

    private VendorVault createVendorVaultWithProducts(Product... products) {
        VendorVault customVendorVault = new VendorVault();
        for (Product product : products) {
            customVendorVault.getInventory().addProduct(product);
        }
        return customVendorVault;
    }
}
