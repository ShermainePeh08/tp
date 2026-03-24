package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Stats strip above the inventory list.
 * Layout in InventoryStatsPanel.fxml, styling in InventoryStatsPanel.css.
 * Mini donuts are drawn with Arc shapes (PieChart is too large at small sizes).
 */
public class InventoryStatsPanel extends UiPart<Region> {

    private static final String FXML = "InventoryStatsPanel.fxml";

    private static final Color COLOR_GREEN = Color.web("#4bac80");
    private static final Color COLOR_RED = Color.web("#e55d5d");
    private static final Color COLOR_GREY = Color.web("#555968");
    private static final Color COLOR_BG = Color.web("#2f323c");

    private static final String COLOR_GREEN_HEX = "#4bac80";
    private static final String COLOR_RED_HEX = "#e55d5d";

    // Stats card labels
    @FXML private Label contactValue;
    @FXML private Label contactTotal;
    @FXML private Label productValue;
    @FXML private Label productTotal;

    // Mini donut panes
    @FXML private Pane contactMiniChart;
    @FXML private Pane productMiniChart;

    // Main donut chart
    @FXML private PieChart pieChart;
    @FXML private Circle donutHole;
    @FXML private Label countLabel;
    @FXML private Label legendInLabel;
    @FXML private Label legendLowLabel;
    @FXML private Circle dotIn;
    @FXML private Circle dotLow;

    /**
     * Creates an {@code InventoryStatsPanel} and binds it to the given observable lists.
     *
     * @param filteredProducts the currently displayed (filtered) product list
     * @param activePersons    the currently active (filtered) contact list
     * @param allPersons       the full unfiltered contact list, used for totals
     * @param allProducts      the full unfiltered product list, used for totals
     */
    public InventoryStatsPanel(ObservableList<Product> filteredProducts,
                               ObservableList<Person> activePersons,
                               ObservableList<Person> allPersons,
                               ObservableList<Product> allProducts) {
        super(FXML);
        initMainDonut();
        bindData(filteredProducts, activePersons, allPersons, allProducts);
    }

    /** Set up the main donut PieChart colours. */
    private void initMainDonut() {
        pieChart.setAnimated(false);
        pieChart.setStartAngle(90);

        donutHole.setFill(COLOR_BG);
        dotIn.setFill(COLOR_GREEN);
        dotLow.setFill(COLOR_RED);

        PieChart.Data sliceIn = new PieChart.Data("In stock", 1);
        PieChart.Data sliceLow = new PieChart.Data("Low stock", 0);

        pieChart.setData(FXCollections.observableArrayList(sliceIn, sliceLow));
        pieChart.setStyle("-fx-background-color: transparent;"
                + "CHART_COLOR_1: " + COLOR_GREEN_HEX + ";"
                + "CHART_COLOR_2: " + COLOR_RED_HEX + ";");

        sliceIn.nodeProperty().addListener((obs, o, node) -> {
            if (node != null) {
                node.setStyle("-fx-pie-color: " + COLOR_GREEN_HEX + "; -fx-stroke: transparent;");
            }
        });

        sliceLow.nodeProperty().addListener((obs, o, node) -> {
            if (node != null) {
                node.setStyle("-fx-pie-color: " + COLOR_RED_HEX + "; -fx-stroke: transparent;");
            }
        });
    }

    /**
     * Draws a mini donut on a Pane using two Arc shapes.
     * @param pane   the 48x48 Pane to draw on
     * @param active number of active items (green arc)
     * @param total  total items (determines grey remainder)
     */
    private void drawMiniDonut(Pane pane, int active, int total) {
        pane.getChildren().clear();

        double cx = 24;
        double cy = 24;
        double outerR = 22;
        double innerR = 13;
        double strokeW = outerR - innerR;
        double r = (outerR + innerR) / 2.0;

        // Background full circle (grey)
        Circle bg = new Circle(cx, cy, r);
        bg.setFill(Color.TRANSPARENT);
        bg.setStroke(COLOR_GREY);
        bg.setStrokeWidth(strokeW);

        // Active arc (green), drawn from 90° clockwise
        double fraction = total > 0 ? (double) active / total : 0;
        double degrees = fraction * 360.0;

        Arc arc = new Arc(cx, cy, r, r, 90, -degrees);
        arc.setType(ArcType.OPEN);
        arc.setFill(Color.TRANSPARENT);
        arc.setStroke(COLOR_GREEN);
        arc.setStrokeWidth(strokeW);
        arc.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.BUTT);

        pane.getChildren().addAll(bg, arc);
    }

    /** Wire all labels and charts to live ObservableLists. */
    private void bindData(ObservableList<Product> filteredProducts,
                        ObservableList<Person> activePersons,
                        ObservableList<Person> allPersons,
                        ObservableList<Product> allProducts) {

        updateContacts(allPersons);
        allPersons.addListener((ListChangeListener<Person>) c ->
                updateContacts(allPersons));

        updateProducts(allProducts);
        allProducts.addListener((ListChangeListener<Product>) c ->
                updateProducts(allProducts));

        refreshDonut(allProducts);
        allProducts.addListener((ListChangeListener<Product>) c ->
                refreshDonut(allProducts));
    }

    private void updateContacts(ObservableList<Person> allPersons) {
        long activeCount = allPersons.stream()
                .filter(person -> !person.isArchived())
                .count();

        contactValue.setText(String.valueOf(activeCount));
        contactTotal.setText("/ " + allPersons.size() + " total");
        drawMiniDonut(contactMiniChart, (int) activeCount, allPersons.size());
    }

    private void updateProducts(ObservableList<Product> allProducts) {
        long activeCount = allProducts.stream()
                .filter(product -> !product.isArchived())
                .count();

        productValue.setText(String.valueOf(activeCount));
        productTotal.setText("/ " + allProducts.size() + " total");
        drawMiniDonut(productMiniChart, (int) activeCount, allProducts.size());
    }

    private void refreshDonut(ObservableList<Product> allProducts) {
        long low = allProducts.stream()
                .filter(product -> !product.isArchived())
                .filter(product -> product.getQuantity().value <= product.getRestockThreshold().value)
                .count();

        long activeProducts = allProducts.stream()
                .filter(product -> !product.isArchived())
                .count();

        long in = activeProducts - low;

        ObservableList<PieChart.Data> data = pieChart.getData();
        if (data != null && data.size() == 2) {
            data.get(0).setPieValue(Math.max(in + 0.0001, 0));
            data.get(1).setPieValue(Math.max(low, 0));
        }

        countLabel.setText(String.valueOf(low));
        legendInLabel.setText("In stock (" + in + ")");
        legendLowLabel.setText("Low stock (" + low + ")");
    }
}
