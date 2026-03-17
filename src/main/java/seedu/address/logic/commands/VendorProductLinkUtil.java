package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.product.Product;

/**
 * Contains vendor-product link operations shared across commands.
 */
final class VendorProductLinkUtil {

    private VendorProductLinkUtil() {}

    /**
     * Returns products currently linked to the given vendor email.
     */
    static List<Product> collectLinkedProducts(Model model, Email email) {
        requireNonNull(model);
        requireNonNull(email);

        List<Product> linkedProducts = new ArrayList<>();
        ObservableList<Product> productList = model.getInventory().getProductList();

        for (Product product : productList) {
            if (product.getVendorEmail().isPresent()) {
                Email vendorEmail = product.getVendorEmail().get();
                if (vendorEmail.equals(email)) {
                    linkedProducts.add(product);
                }
            }
        }

        return linkedProducts;
    }

    /**
     * Clears vendor email links for the provided products.
     */
    static void clearVendorEmail(Model model, List<Product> linkedProducts) {
        requireNonNull(model);
        requireNonNull(linkedProducts);

        for (Product linkedProduct : linkedProducts) {
            model.setProduct(linkedProduct, linkedProduct.clearVendorEmail());
        }
    }

    /**
     * Sets vendor email links for the provided products to {@code newEmail}.
     */
    static void updateVendorEmail(Model model, List<Product> linkedProducts, Email newEmail) {
        requireNonNull(model);
        requireNonNull(linkedProducts);
        requireNonNull(newEmail);

        for (Product linkedProduct : linkedProducts) {
            model.setProduct(linkedProduct, linkedProduct.withVendorEmail(newEmail));
        }
    }
}
