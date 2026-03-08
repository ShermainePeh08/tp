package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProducts.getTypicalInventory;

import org.junit.jupiter.api.Test;

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

    private VendorVault createTypicalVendorVault() {
        VendorVault typicalVendorVault = new VendorVault();
        typicalVendorVault.setAddressBook(getTypicalAddressBook());
        typicalVendorVault.setInventory(getTypicalInventory());
        return typicalVendorVault;
    }
}
