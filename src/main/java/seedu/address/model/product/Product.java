package seedu.address.model.product.exceptions;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.product.Identifier;
import seedu.address.model.product.Name;
import seedu.address.model.product.Quantity;

/**
 * Represents a Product in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {

    // Identity fields
    private final Identifier identifier;
    private final Name name;

    // Data fields
    private final Quantity quantity;

    /**
     * Every field must be present and not null.
     */
    public Product(Identifier identifier, Name name, Quantity quantity) {
        requireAllNonNull(identifier, name, quantity);
        this.identifier = identifier;
        this.name = name;
        this.quantity = quantity;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Returns true if both products have the same name.
     * This defines a weaker notion of equality between two products.
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct == this) {
            return true;
        }

        return otherProduct != null
                && otherProduct.getName().equals(getName());
    }

    /**
     * Returns true if both products have the same identifier.
     * This defines a stronger notion of equality between two products.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Product otherProduct)) {
            return false;
        }

        return identifier.equals(otherProduct.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, quantity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", identifier)
                .add("name", name)
                .add("quantity", quantity)
                .toString();
    }

}
