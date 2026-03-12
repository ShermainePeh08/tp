package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Identifier;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.Quantity;

/**
 * Jackson-friendly version of {@link Product}
 */
public class JsonAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing";

    // Identity fields
    private final String identifier;

    // Data fields
    private final String name;
    private final String quantity;
    private final boolean isArchived;

    /**
     * Constructs a {@Code JsonAdapatedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("identfier") String identifier, @JsonProperty("name") String name,
            @JsonProperty("quantity") String quantity, @JsonProperty("isArchived") boolean isArchived) {
        this.identifier = identifier;
        this.name = name;
        this.quantity = quantity;
        this.isArchived = isArchived;
    }

    /**
     * Converts a given {@code Product} into this class for Jackson use.
     */
    public JsonAdaptedProduct(Product product) {
        this.identifier = product.getIdentifier().toString();
        this.name = product.getName().fullName;
        this.quantity = product.getQuantity().toString();
        this.isArchived = product.isArchived();
    }

    /**
     * Converts this Jackson-friendly adapted product into the model's {@code Product} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Product toModelType() throws IllegalValueException {
        if (identifier == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Identifier.class.getSimpleName()));
        }
        if (!Identifier.isValidIdentifier(identifier)) {
            throw new IllegalValueException(Identifier.MESSAGE_CONSTRAINTS);
        }
        final Identifier modelIdentifier = new Identifier(identifier);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        return new Product(modelIdentifier, modelName, modelQuantity, isArchived);
    }
}
