package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import seedu.address.model.product.Product;

/**
 * Displays a donut chart (stock status) on the left and an empty
 * placeholder on the right, above the inventory list.
 */
public class InventoryStatsPanel {

    private static final String COLOR_GREEN = "#4bac80";
    private static final String COLOR_RED = "#e55d5d";
    private static final String COLOR_BG = "#2b2d3e";
    private static final String COLOR_SUBTEXT = "#aaaaaa";

    private static final double STRIP_HEIGHT = 220;
    private static final double PIE_SIZE = 160;
    private static final double HOLE_RADIUS = 40;

    private static final int CARD_SPACING = 10;
    private static final int PADDING_TOP = 10;
    private static final int PADDING_RIGHT = 10;
    private static final int PADDING_BOTTOM = 20;
    private static final int PADDING_LEFT = 10;

    private final HBox root;

    public InventoryStatsPanel(ObservableList<Product> productList) {
        root = buildRoot(productList);
    }

    public Region getRoot() {
        return root;
    }

    private HBox buildRoot(ObservableList<Product> products) {
        Region left = buildDonutCard(products);
        Region right = buildEmptyCard();

        HBox box = new HBox(CARD_SPACING, left, right);
        box.widthProperty().addListener((obs, oldVal, newVal) -> {
            double half = (newVal.doubleValue() - CARD_SPACING) / 2.0;
            left.setPrefWidth(half);
            right.setPrefWidth(half);
        });

        box.setPadding(new Insets(
                PADDING_TOP,
                PADDING_RIGHT,
                PADDING_BOTTOM,
                PADDING_LEFT
        ));

        box.setPrefHeight(STRIP_HEIGHT);
        box.setFillHeight(true);
        box.setMaxHeight(STRIP_HEIGHT);
        box.setMinHeight(STRIP_HEIGHT);
        box.setStyle("-fx-background-color: transparent;");
        return box;
    }

    private Region buildDonutCard(ObservableList<Product> products) {
        long total = products.size();
        long lowStock = products.stream()
                .filter(p -> p.getQuantity().value <= p.getRestockThreshold().value)
                .count();
        long inStock = total - lowStock;

        PieChart.Data sliceIn = new PieChart.Data("In stock", Math.max(inStock, 0));
        PieChart.Data sliceLow = new PieChart.Data("Low stock", Math.max(lowStock, 0));

        PieChart pie = new PieChart(FXCollections.observableArrayList(sliceIn, sliceLow));
        pie.setLabelsVisible(false);
        pie.setLegendVisible(false);
        pie.setAnimated(false);
        pie.setStartAngle(90);
        pie.setClockwise(true);
        pie.setPrefSize(PIE_SIZE, PIE_SIZE);
        pie.setMaxSize(PIE_SIZE, PIE_SIZE);
        pie.setMinSize(PIE_SIZE, PIE_SIZE);

        pie.setStyle(
                "-fx-background-color: transparent;"
                + "CHART_COLOR_1: " + COLOR_GREEN + ";"
                + "CHART_COLOR_2: " + COLOR_RED + ";");

        sliceIn.nodeProperty().addListener((obs, o, node) -> {
            if (node != null) {
                node.setStyle("-fx-pie-color: " + COLOR_GREEN + ";");
            }
        });
        sliceLow.nodeProperty().addListener((obs, o, node) -> {
            if (node != null) {
                node.setStyle("-fx-pie-color: " + COLOR_RED + ";");
            }
        });

        Circle hole = new Circle(HOLE_RADIUS);
        hole.setFill(Color.web(COLOR_BG));

        Label countLbl = new Label(String.valueOf(lowStock));
        countLbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        Label subLbl = new Label("low stock");
        subLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: " + COLOR_SUBTEXT + ";");

        VBox centreBox = new VBox(0, countLbl, subLbl);
        centreBox.setAlignment(Pos.CENTER);

        StackPane donutPane = new StackPane(pie, hole, centreBox);
        donutPane.setPrefSize(PIE_SIZE, PIE_SIZE);

        Rectangle clip = new Rectangle(PIE_SIZE, PIE_SIZE);
        clip.setArcWidth(PIE_SIZE);
        clip.setArcHeight(PIE_SIZE);
        donutPane.setClip(clip);

        HBox legendIn = legendDot(COLOR_GREEN, "In stock (" + inStock + ")");
        HBox legendLow = legendDot(COLOR_RED, "Low stock (" + lowStock + ")");
        VBox legend = new VBox(6, legendIn, legendLow);
        legend.setAlignment(Pos.CENTER_LEFT);

        HBox row = new HBox(12, donutPane, legend);
        row.setAlignment(Pos.CENTER);

        return card("Stock Status", row);
    }

    private Region buildEmptyCard() {
        return card("", new HBox());
    }

    private VBox card(String title, Region content) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(10));
        card.setStyle(
            "-fx-background-color: " + COLOR_BG + ";"
            + "-fx-background-radius: 8;");

        if (!title.isEmpty()) {
            Label titleLabel = new Label(title);
            titleLabel.setStyle(
                    "-fx-font-size: 13px; -fx-font-weight: bold;"
                    + "-fx-text-fill: " + COLOR_SUBTEXT + ";");
            card.getChildren().addAll(titleLabel, content);
        } else {
            card.getChildren().add(content);
        }

        card.setPrefWidth(0);
        card.setMaxHeight(Double.MAX_VALUE);
        return card;
    }

    private HBox legendDot(String color, String text) {
        Circle dot = new Circle(5);
        dot.setFill(Color.web(color));
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + COLOR_SUBTEXT + ";");
        HBox row = new HBox(5, dot, lbl);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}
