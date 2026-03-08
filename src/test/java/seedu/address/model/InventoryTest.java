package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_AIRPODS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_IPHONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProducts.OIL;
import static seedu.address.testutil.TypicalProducts.getTypicalInventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.product.Product;
import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.testutil.ProductBuilder;

public class InventoryTest {

    private final Inventory inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventory.getProductList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyInventory_replacesData() {
        Inventory newData = getTypicalInventory();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void resetData_withDuplicateProducts_throwsDuplicateProductException() {
        // Two products with the same identity fields
        Product editedAlice = new ProductBuilder(OIL).withName(VALID_PRODUCT_NAME_AIRPODS)
                .withQuantity(VALID_QUANTITY_IPHONE).build();
        List<Product> newProducts = Arrays.asList(OIL, editedAlice);
        InventoryStub newData = new InventoryStub(newProducts);

        assertThrows(DuplicateProductException.class, () -> inventory.resetData(newData));
    }

    @Test
    public void hasProduct_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventory.hasProduct(null));
    }

    @Test
    public void hasProduct_productNotInInventory_returnsFalse() {
        assertFalse(inventory.hasProduct(OIL));
    }

    @Test
    public void hasProduct_productInInventory_returnsTrue() {
        inventory.addProduct(OIL);
        assertTrue(inventory.hasProduct(OIL));
    }

    @Test
    public void hasProduct_productWithSameIdentityFieldsInInventory_returnsTrue() {
        inventory.addProduct(OIL);
        Product editedAlice = new ProductBuilder(OIL).withName(VALID_PRODUCT_NAME_AIRPODS)
                .withQuantity(VALID_QUANTITY_IPHONE).build();
        assertTrue(inventory.hasProduct(editedAlice));
    }

    @Test
    public void getProductList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> inventory.getProductList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = Inventory.class.getCanonicalName() + "{products=" + inventory.getProductList() + "}";
        assertEquals(expected, inventory.toString());
    }

    /**
     * A stub ReadOnlyInventory whose products list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyInventory {
        private final ObservableList<Product> products = FXCollections.observableArrayList();

        InventoryStub(Collection<Product> products) {
            this.products.setAll(products);
        }

        @Override
        public ObservableList<Product> getProductList() {
            return products;
        }
    }

}
