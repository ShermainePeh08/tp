package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VendorVault;
import seedu.address.model.product.Product;
import seedu.address.testutil.ProductBuilder;

/**
 * Tests for {@link DeleteProductCommand}.
 */
public class DeleteProductCommandTest {

    /**
     * Tests that deleting a non-existent product throws an exception.
     */
    @Test
    public void execute_invalidProduct_throwsCommandException() {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        DeleteProductCommand command = new DeleteProductCommand("INVALID", true);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    /**
     * Tests deleting a valid product.
     */
    @Test
    public void execute_validProduct_success() throws Exception {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        Product product = new ProductBuilder().build();
        model.addProduct(product);

        DeleteProductCommand command =
                new DeleteProductCommand(product.getIdentifier().toString(), false);

        command.execute(model);

        assertEquals(0, model.getFilteredProductList().size());
    }

    /**
     * Tests equality logic.
     */
    @Test
    public void equals() {
        DeleteProductCommand first = new DeleteProductCommand("P001", true);
        DeleteProductCommand second = new DeleteProductCommand("P001", true);
        DeleteProductCommand third = new DeleteProductCommand("P002", true);

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
        assertFalse(first.equals(null));
    }

    @Test
    public void execute_needsConfirmation_setsPendingConfirmation() throws Exception {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        Product product = new ProductBuilder().build();
        model.addProduct(product);

        DeleteProductCommand command =
                new DeleteProductCommand(product.getIdentifier().toString(), true);

        command.execute(model);

        assertNotNull(command.getPendingConfirmation());
    }

    @Test
    public void onConfirm_deletesProduct() throws Exception {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        Product product = new ProductBuilder().build();
        model.addProduct(product);

        DeleteProductCommand command =
                new DeleteProductCommand(product.getIdentifier().toString(), false);

        command.onConfirm(model, product);

        assertEquals(0, model.getFilteredProductList().size());
    }

    @Test
    public void onCancel_returnsFailureMessage() {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        DeleteProductCommand command =
                new DeleteProductCommand("P001", true);

        Optional<CommandResult> result = command.onCancel(model);

        assertTrue(result.isPresent());
        assertEquals(DeleteProductCommand.MESSAGE_DELETE_FAILURE,
                result.get().getFeedbackToUser());
    }

    @Test
    public void toString_containsIdentifier() {
        DeleteProductCommand command = new DeleteProductCommand("P001", true);

        String str = command.toString();

        assertTrue(str.contains("P001"));
    }
}
