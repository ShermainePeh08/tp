package seedu.address.model.product;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Represents a product predicate that provides a comparator.
 */
public interface RankedProductPredicate extends Predicate<Product> {

    Comparator<Product> createProductComparator();
}
