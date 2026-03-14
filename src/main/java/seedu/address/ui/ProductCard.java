package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.product.Product;

/**
 * UI component that represents a product card.
 */
public class ProductCard extends UiPart<HBox> {

    private static final String FXML = "ProductCard.fxml";

    public final Product product;

    @FXML
    private Label productName;

    @FXML
    private Label quantity;

    /**
     * Creates a ProductCard displaying the specified {@code Product}.
     *
     * @param product Product to display.
     * @param displayedIndex Index shown in the inventory list.
     */
    public ProductCard(Product product, int displayedIndex) {
        super(FXML);
        this.product = product;
        String name = product.getName().toString();
        int qty = product.getQuantity().value;
        int threshold = product.getRestockThreshold().value;

        productName.setText(displayedIndex + ". " + name);
        quantity.setText("Stock: " + qty);

        if (qty > threshold) {
            quantity.setStyle("-fx-text-fill: #00ff7f;");
        } else {
            quantity.setStyle("-fx-text-fill: #ff4c4c;");
        }
    }
}
