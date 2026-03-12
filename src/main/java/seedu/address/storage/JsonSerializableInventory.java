package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonRootName(value = "inventory")
class JsonSerializableInventory {

    private final List<JsonAdaptedProduct> products = new ArrayList<>();

    @JsonCreator
    public JsonSerializableInventory(@JsonProperty("products") List<JsonAdaptedProduct> products) {
        this.products.addAll(products);
    }

    public JsonSerializableInventory(ReadOnlyInventory source) {
        products.addAll(source.getProductList().stream().map(JsonAdaptedProduct::new).collect(Collectors.toList()));
    }

    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();
        for (JsonAdaptedProduct jsonAdaptedProduct : products) {
            Product product = jsonAdaptedProduct.toModelType();
            inventory.addProduct(product);
        }

        return inventory;
    }
}
