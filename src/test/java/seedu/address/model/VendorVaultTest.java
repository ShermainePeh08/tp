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
import seedu.address.testutil.PersonBuilder;

public class VendorVaultTest {

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

    @Test
    public void findSimilarNameMatch_matchExists_returnsMatchingPerson() {
        Person existingPerson = new PersonBuilder()
                .withName("Acme Supplies")
                .withPhone("90000001")
                .withEmail("acme@example.com")
                .withAddress("10 Business Park")
                .build();
        VendorVault vendorVault = createVendorVaultWithPersons(existingPerson);

        Person candidate = new PersonBuilder()
                .withName("Acme Partners")
                .withPhone("90000002")
                .withEmail("candidate@example.com")
                .withAddress("99 Other Street")
                .build();

        assertEquals(Optional.of(existingPerson), vendorVault.findSimilarNameMatch(candidate, null));
    }

    @Test
    public void findSimilarNameMatch_matchingPersonExcluded_returnsEmpty() {
        Person existingPerson = new PersonBuilder()
                .withName("Acme Supplies")
                .withPhone("90000003")
                .withEmail("acme2@example.com")
                .withAddress("11 Business Park")
                .build();
        VendorVault vendorVault = createVendorVaultWithPersons(existingPerson);

        Person candidate = new PersonBuilder()
                .withName("Acme Partners")
                .withPhone("90000004")
                .withEmail("candidate2@example.com")
                .withAddress("100 Other Street")
                .build();

        assertEquals(Optional.empty(), vendorVault.findSimilarNameMatch(candidate, existingPerson));
    }

    @Test
    public void findSimilarAddressMatch_matchExists_returnsMatchingPerson() {
        Person existingPerson = new PersonBuilder()
                .withName("North Vendor")
                .withPhone("90000005")
                .withEmail("north@example.com")
                .withAddress("123 Woodlands Avenue 5")
                .build();
        VendorVault vendorVault = createVendorVaultWithPersons(existingPerson);

        Person candidate = new PersonBuilder()
                .withName("South Vendor")
                .withPhone("90000006")
                .withEmail("south@example.com")
                .withAddress("Woodlands")
                .build();

        assertEquals(Optional.of(existingPerson), vendorVault.findSimilarAddressMatch(candidate, null));
    }

    @Test
    public void findSimilarAddressMatch_matchingPersonExcluded_returnsEmpty() {
        Person existingPerson = new PersonBuilder()
                .withName("North Vendor")
                .withPhone("90000007")
                .withEmail("north2@example.com")
                .withAddress("456 Clementi Road")
                .build();
        VendorVault vendorVault = createVendorVaultWithPersons(existingPerson);

        Person candidate = new PersonBuilder()
                .withName("West Vendor")
                .withPhone("90000008")
                .withEmail("west@example.com")
                .withAddress("Clementi")
                .build();

        assertEquals(Optional.empty(), vendorVault.findSimilarAddressMatch(candidate, existingPerson));
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
}
