package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProducts.getTypicalInventory;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VendorVault;
import seedu.address.model.product.Product;
import seedu.address.testutil.ProductBuilder;

public class ClearProductCommandTest {

    @Test
    public void execute_needsConfirmation_setsPendingConfirmation() throws Exception {
        Model model = new ModelManager(
            new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
            new UserPrefs());

        Product product = new ProductBuilder().build();
        model.addProduct(product);

        ClearProductCommand command = new ClearProductCommand(true);

        command.execute(model);

        assertNotNull(command.getPendingConfirmation());
    }

    @Test
    public void onConfirm_clearsProducts() {
        Model model = new ModelManager(
            new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
            new UserPrefs());

        Product product = new ProductBuilder().build();
        model.addProduct(product);

        ClearProductCommand command = new ClearProductCommand(false);

        command.onConfirm(model);

        assertEquals(0, model.getFilteredProductList().size());
    }

    @Test
    public void onCancel_returnsCancelMessage() {
        Model model = new ModelManager(
            new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
            new UserPrefs());

        ClearProductCommand command = new ClearProductCommand(true);

        Optional<CommandResult> result = command.onCancel(model);

        assertTrue(result.isPresent());
        assertEquals(ClearProductCommand.MESSAGE_CANCELLED,
                result.get().getFeedbackToUser());
    }

    @Test
    public void execute_noConfirmation_clearsProducts() throws Exception {
        Model model = new ModelManager(
            new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
            new UserPrefs());

        ClearProductCommand command = new ClearProductCommand(false);

        command.execute(model);

        assertTrue(model.getFilteredProductList().isEmpty());
    }

    @Test
    public void execute_withConfirmation_setsPendingConfirmation() throws Exception {
        Model model = new ModelManager(
                new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
                new UserPrefs());

        ClearProductCommand command = new ClearProductCommand(true);

        CommandResult result = command.execute(model);

        assertEquals(ClearProductCommand.MESSAGE_CONFIRMATION, result.getFeedbackToUser());
        assertNotNull(command.getPendingConfirmation());
    }

    @Test
    public void onCancel_returnsCancelledMessage() {
        Model model = new ModelManager(
            new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
            new UserPrefs());

        ClearProductCommand command = new ClearProductCommand(true);

        CommandResult result = command.onCancel(model).get();

        assertEquals(ClearProductCommand.MESSAGE_CANCELLED, result.getFeedbackToUser());
    }

    @Test
    public void execute_confirm_clearsProducts() throws Exception {
        Model model = new ModelManager(
                new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
                new UserPrefs());

        ClearProductCommand command = new ClearProductCommand(true);

        command.execute(model);

        PendingConfirmation pending = command.getPendingConfirmation();

        ConfirmCommand confirmCommand = new ConfirmCommand(pending.getOnConfirm());

        confirmCommand.execute(model);

        assertTrue(model.getFilteredProductList().isEmpty());
    }

    @Test
    public void execute_cancel_doesNotClearProducts() throws Exception {
        Model model = new ModelManager(
                new VendorVault(getTypicalAddressBook(), getTypicalInventory()),
                new UserPrefs());

        ClearProductCommand command = new ClearProductCommand(true);

        command.execute(model);

        PendingConfirmation pending = command.getPendingConfirmation();

        CancelCommand cancelCommand = new CancelCommand(pending.getOnCancel());

        cancelCommand.execute(model);

        assertFalse(model.getFilteredProductList().isEmpty());
    }

    @Test
    public void equals() {
        ClearProductCommand first = new ClearProductCommand(true);
        ClearProductCommand second = new ClearProductCommand(true);
        ClearProductCommand third = new ClearProductCommand(false);

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(null));
    }

    @Test
    public void toStringMethod() {
        ClearProductCommand command = new ClearProductCommand(true);

        String expected = "ClearProductCommand(needsConfirmation=true)";
        assertEquals(expected, command.toString());
    }
}
