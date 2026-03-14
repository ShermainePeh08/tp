package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VendorVault;
import seedu.address.model.product.Product;
import seedu.address.testutil.ProductBuilder;

public class ClearProductCommandTest {

    @Test
    public void execute_needsConfirmation_setsPendingConfirmation() throws Exception {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        Product product = new ProductBuilder().build();
        model.addProduct(product);

        ClearProductCommand command = new ClearProductCommand(true);

        command.execute(model);

        assertNotNull(command.getPendingConfirmation());
    }

    @Test
    public void onConfirm_clearsProducts() {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        Product product = new ProductBuilder().build();
        model.addProduct(product);

        ClearProductCommand command = new ClearProductCommand(false);

        command.onConfirm(model);

        assertEquals(0, model.getFilteredProductList().size());
    }

    @Test
    public void onCancel_returnsCancelMessage() {
        ModelManager model = new ModelManager(new VendorVault(), new UserPrefs());

        ClearProductCommand command = new ClearProductCommand(true);

        Optional<CommandResult> result = command.onCancel(model);

        assertTrue(result.isPresent());
        assertEquals(ClearProductCommand.MESSAGE_CANCELLED,
                result.get().getFeedbackToUser());
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
}
