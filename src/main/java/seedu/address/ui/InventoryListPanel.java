package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Displays the inventory list in the right panel.
 */
public class InventoryListPanel extends UiPart<Region> {

    private static final String FXML = "InventoryListPanel.fxml";
    private static final int LOW_STOCK_THRESHOLD = 10;

    @FXML
    private ListView<String> inventoryListView;

    /**
     * Creates an InventoryListPanel with sample inventory data.
     */
    public InventoryListPanel() {
        super(FXML);

        ObservableList<String> fakeInventory = FXCollections.observableArrayList(
                "Product A:001:25",
                "Product B:002:5000",
                "Product C:003:999999",
                "Product D:004:3"
        );

        SortedList<String> sortedInventory = new SortedList<>(fakeInventory, (a, b) -> {

            String[] pa = a.split(":");
            String[] pb = b.split(":");

            int qtyA = Integer.parseInt(pa[2]);
            int qtyB = Integer.parseInt(pb[2]);

            boolean lowA = qtyA <= LOW_STOCK_THRESHOLD;
            boolean lowB = qtyB <= LOW_STOCK_THRESHOLD;

            if (lowA && !lowB) {
                return -1;
            }

            if (!lowA && lowB) {
                return 1;
            }

            return Integer.compare(qtyA, qtyB);
        });

        inventoryListView.setItems(sortedInventory);

        inventoryListView.setCellFactory(list -> new ListCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                String[] parts = item.split(":");

                String name = parts[0];
                String id = parts[1];
                int qty = Integer.parseInt(parts[2]);

                Label idLabel = new Label(id);
                Label nameLabel = new Label(name);
                Label qtyLabel = new Label(String.valueOf(qty));

                idLabel.setPrefWidth(80);
                nameLabel.setPrefWidth(200);

                nameLabel.setTextFill(Color.WHITE);
                idLabel.setTextFill(Color.LIGHTGRAY);

                // quantity pill sizing
                qtyLabel.setAlignment(Pos.CENTER);
                qtyLabel.setMinWidth(40);
                qtyLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
                qtyLabel.setMaxWidth(80);

                if (qty <= LOW_STOCK_THRESHOLD) {
                    qtyLabel.setStyle(
                        "-fx-background-color: #e55d5d;"
                        + "-fx-background-radius: 12;"
                        + "-fx-padding: 4 12 4 12;"
                        + "-fx-text-fill: white;"
                    );
                } else {
                    qtyLabel.setStyle(
                        "-fx-background-color: #4bac80;"
                        + "-fx-background-radius: 12;"
                        + "-fx-padding: 4 12 4 12;"
                        + "-fx-text-fill: white;"
                    );
                }

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox row = new HBox(idLabel, nameLabel, spacer, qtyLabel);
                row.setSpacing(20);
                row.setStyle("-fx-padding: 10 20 10 20;");
                row.setAlignment(Pos.CENTER_LEFT);

                setGraphic(row);
                setText(null);
            }
        });
    }
}
